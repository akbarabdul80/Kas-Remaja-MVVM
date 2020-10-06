package com.zerodev.kasremaja.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zerodev.kasremaja.data.model.login.ResponseLogin

class DashboardViewModel : ViewModel() {
    val progressLogin = MutableLiveData<Boolean>()
    val responseLogin = MutableLiveData<ResponseLogin>()
    val errorLogin = MutableLiveData<Boolean>()

    val showMessage = MutableLiveData<String>()
}