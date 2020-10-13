package com.zerodev.kasremaja.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zerodev.kasremaja.data.model.history.DataHistory
import com.zerodev.kasremaja.root.App

class HistoryViewModel :
    ViewModel(), HistoryContract {

    private val observer : MutableLiveData<HistoryState> by lazy {
        MutableLiveData<HistoryState>()
    }

    override val state: LiveData<HistoryState>
        get() = observer

    override fun getData() {
        App.service!!.getHistory()
            .map<HistoryState>(HistoryState::Result)
            .onErrorReturn(HistoryState::Error)
            .toFlowable()
            .startWith(HistoryState.Loading)
            .subscribe(observer::postValue)
            .let { return@let App.disposable::add }
    }
}