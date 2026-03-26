package com.theproductarchitect.wave.nfc

enum class NfcState {
    IDLE,
    WAITING_READ,
    WAITING_WRITE,
    SUCCESS_READ,
    SUCCESS_WRITE,
    ERROR
}
