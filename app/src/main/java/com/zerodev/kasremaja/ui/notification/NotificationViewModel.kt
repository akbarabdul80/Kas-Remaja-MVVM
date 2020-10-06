package com.zerodev.kasremaja.ui.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zerodev.kasremaja.data.model.notification.ResponseNotification

class NotificationViewModel : ViewModel() {
    val progressLogin = MutableLiveData<Boolean>()
    val responseLogin = MutableLiveData<ResponseNotification>()
    val errorLogin = MutableLiveData<Boolean>()

    val showMessage = MutableLiveData<String>()
}