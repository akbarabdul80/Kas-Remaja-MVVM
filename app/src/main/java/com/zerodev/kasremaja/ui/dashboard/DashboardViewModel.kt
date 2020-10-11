package com.zerodev.kasremaja.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zerodev.kasremaja.root.App

class DashboardViewModel :
    ViewModel(), DashboardContract {

    private val observer : MutableLiveData<DashboardState> by lazy {
        MutableLiveData<DashboardState>()
    }

    override val state: LiveData<DashboardState>
        get() = observer

    override fun getData(id_user: String) {
        App.service!!.getKas(
            id_user
        )
            .map<DashboardState>(DashboardState::Result)
            .onErrorReturn(DashboardState::Error)
            .toFlowable()
            .startWith(DashboardState.Loading)
            .subscribe(observer::postValue)
            .let { return@let App.disposable::add }
    }

}