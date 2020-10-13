package com.zerodev.kasremaja.ui.history

import com.zerodev.kasremaja.data.model.history.ResponseHistory

sealed class HistoryState {
    object Loading : HistoryState()

    data class Result(val data : ResponseHistory) : HistoryState()
    data class Error(val error : Throwable) : HistoryState()
}