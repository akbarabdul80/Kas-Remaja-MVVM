package com.zerodev.kasremaja.ui.history

import androidx.lifecycle.LiveData

interface HistoryContract {
    val state: LiveData<HistoryState>

    fun getData()
}