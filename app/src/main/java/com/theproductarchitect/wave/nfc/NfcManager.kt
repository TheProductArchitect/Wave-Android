package com.theproductarchitect.wave.nfc

import android.app.Activity
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.os.Bundle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * NfcManager handles the low-level NFC operations including reading,
 * formatting, and writing NDEF messages to NFC tags.
 * It strictly acts as a singleton adapter interface between Android's NFC hardware and the Compose UI.
 */
/**
 * Handles all core interactions with the physical NFC adapter hardware on the Android device.
 * Operates in reader-mode, exposing tag discovery callbacks and parsing NDEF records
 * (both string content and payload metadata) seamlessly to Jetpack Compose via StateFlows.
 */
@Singleton
class NfcManager @Inject constructor() : NfcAdapter.ReaderCallback {

    private var nfcAdapter: NfcAdapter? = null
    
    private val _nfcState = MutableStateFlow(NfcState.IDLE)
    val nfcState: StateFlow<NfcState> = _nfcState.asStateFlow()

    private val _tagPayload = MutableStateFlow<String?>(null)
    val tagPayload: StateFlow<String?> = _tagPayload.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private var pendingWriteUrl: String? = null
    private var mode: OperationMode = OperationMode.READ

    enum class OperationMode { READ, WRITE }

    fun enableReaderMode(activity: Activity) {
        nfcAdapter = NfcAdapter.getDefaultAdapter(activity)
        val options = Bundle()
        options.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 250)
        
        nfcAdapter?.enableReaderMode(
            activity,
            this,
            NfcAdapter.FLAG_READER_NFC_A or 
            NfcAdapter.FLAG_READER_NFC_B or 
            NfcAdapter.FLAG_READER_NFC_F or 
            NfcAdapter.FLAG_READER_NFC_V,
            options
        )
    }

    fun disableReaderMode(activity: Activity) {
        nfcAdapter?.disableReaderMode(activity)
    }

    fun startReading() {
        mode = OperationMode.READ
        _nfcState.value = NfcState.WAITING_READ
        _tagPayload.value = null
        _errorMessage.value = null
    }

    fun startWriting(url: String) {
        mode = OperationMode.WRITE
        pendingWriteUrl = url
        _nfcState.value = NfcState.WAITING_WRITE
        _tagPayload.value = null
        _errorMessage.value = null
    }

    fun resetState() {
        _nfcState.value = NfcState.IDLE
        _tagPayload.value = null
        _errorMessage.value = null
        pendingWriteUrl = null
    }

    override fun onTagDiscovered(tag: Tag?) {
        if (tag == null) return
        
        // Clear previous error message and reset transient states for fresh tag attempt
        _errorMessage.value = null

        when (mode) {
            OperationMode.READ -> handleReadTag(tag)
            OperationMode.WRITE -> handleWriteTag(tag)
        }
    }

    private fun handleReadTag(tag: Tag) {
        try {
            val ndef = Ndef.get(tag)
            if (ndef == null) {
                _errorMessage.value = "Tag is not NDEF formatted."
                _nfcState.value = NfcState.ERROR
                return
            }

            ndef.connect()
            val message = ndef.ndefMessage
            ndef.close()

            if (message != null && message.records.isNotEmpty()) {
                val record = message.records[0]
                val payloadText = parseRecord(record)
                _tagPayload.value = payloadText
                _nfcState.value = NfcState.SUCCESS_READ
            } else {
                _tagPayload.value = "Empty Tag"
                _nfcState.value = NfcState.SUCCESS_READ
            }
        } catch (e: Exception) {
            _errorMessage.value = "Failed to read tag: ${e.message}"
            _nfcState.value = NfcState.ERROR
        }
    }

    private fun handleWriteTag(tag: Tag) {
        val url = pendingWriteUrl
        if (url == null) {
            _errorMessage.value = "No URL specified for writing."
            _nfcState.value = NfcState.ERROR
            return
        }

        try {
            val uriRecord = NdefRecord.createUri(url)
            val ndefMessage = NdefMessage(arrayOf(uriRecord))
            
            val messageSize = ndefMessage.toByteArray().size

            val ndef = Ndef.get(tag)
            if (ndef != null) {
                ndef.connect()
                if (!ndef.isWritable) {
                    _errorMessage.value = "Tag is read-only."
                    _nfcState.value = NfcState.ERROR
                    ndef.close()
                    return
                }

                if (ndef.maxSize < messageSize) {
                    _errorMessage.value = "Tag capacity is too small."
                    _nfcState.value = NfcState.ERROR
                    ndef.close()
                    return
                }

                ndef.writeNdefMessage(ndefMessage)
                ndef.close()
                _tagPayload.value = url
                _nfcState.value = NfcState.SUCCESS_WRITE
            } else {
                val formatable = NdefFormatable.get(tag)
                if (formatable != null) {
                    formatable.connect()
                    formatable.format(ndefMessage)
                    formatable.close()
                    _tagPayload.value = url
                    _nfcState.value = NfcState.SUCCESS_WRITE
                } else {
                    _errorMessage.value = "Tag does not support NDEF."
                    _nfcState.value = NfcState.ERROR
                }
            }
        } catch (e: IOException) {
            _errorMessage.value = "Connection to tag lost during write."
            _nfcState.value = NfcState.ERROR
        } catch (e: Exception) {
            _errorMessage.value = "Failed to write: ${e.message}"
            _nfcState.value = NfcState.ERROR
        }
    }

    private fun parseRecord(record: NdefRecord): String {
        return try {
            if (record.tnf == NdefRecord.TNF_WELL_KNOWN && java.util.Arrays.equals(record.type, NdefRecord.RTD_TEXT)) {
                val payload = record.payload
                val textEncoding = if ((payload[0].toInt() and 128) == 0) "UTF-8" else "UTF-16"
                val languageCodeLength = payload[0].toInt() and 63
                String(payload, languageCodeLength + 1, payload.size - languageCodeLength - 1, Charsets.UTF_8)
            } else if (record.tnf == NdefRecord.TNF_WELL_KNOWN && java.util.Arrays.equals(record.type, NdefRecord.RTD_URI)) {
                record.toUri()?.toString() ?: String(record.payload, Charsets.UTF_8)
            } else {
                String(record.payload, Charsets.UTF_8)
            }
        } catch (e: Exception) {
            "Unknown format"
        }
    }
}
