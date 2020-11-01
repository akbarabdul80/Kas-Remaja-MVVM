package com.zerodev.kasremaja.ui.profile.editPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zerodev.kasremaja.root.App
import com.zerodev.kasremaja.ui.profile.editProfile.EditState

class PasswordViewModel :
        ViewModel(), PasswordContract{

    private val observer : MutableLiveData<PasswordState> by lazy {
        MutableLiveData<PasswordState>()
    }


    override val state: LiveData<PasswordState>
        get() = observer

    override fun updatePass(oldPass: String, newPass: String, rePass: String) {
        App.service!!.updatePass(oldPass, newPass, rePass)
            .map<PasswordState>(PasswordState::Result)
            .onErrorReturn(PasswordState::Error)
            .toFlowable()
            .startWith(PasswordState.loading)
            .subscribe(observer::postValue)
            .let { return@let App.disposable::add }
    }

}