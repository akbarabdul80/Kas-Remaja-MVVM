package com.zerodev.kasremaja.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zerodev.kasremaja.root.App

class NotificationViewModel :
    ViewModel(), NotificationContract {

    private val observer : MutableLiveData<NotificationState> by lazy {
        MutableLiveData<NotificationState>()
    }

    override val state: LiveData<NotificationState>
        get() = observer

    override fun getData(id_user: String) {
        App.service!!.getNotification(id_user)
            .map<NotificationState>(NotificationState::Result)
            .onErrorReturn(NotificationState::Error)
            .toFlowable()
            .startWith(NotificationState.Loading)
            .subscribe(observer::postValue)
            .let { return@let App.disposable::add }
    }

}