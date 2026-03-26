package com.example.wave

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wave.nfc.NfcManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WaveViewModel @Inject constructor(
    val nfcManager: NfcManager
) : ViewModel() {

    val nfcState = nfcManager.nfcState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        com.example.wave.nfc.NfcState.IDLE
    )
    
    val tagPayload = nfcManager.tagPayload.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )
    
    val errorMessage = nfcManager.errorMessage.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    fun startReading() {
        nfcManager.startReading()
    }

    fun startWriting(url: String) {
        nfcManager.startWriting(url)
    }

    fun resetNfcState() {
        nfcManager.resetState()
    }
}
