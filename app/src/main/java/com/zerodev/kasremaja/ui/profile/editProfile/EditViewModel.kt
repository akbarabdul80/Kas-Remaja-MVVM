package com.zerodev.kasremaja.ui.profile.editProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zerodev.kasremaja.root.App

class EditViewModel:
    ViewModel(), EditContract{

    private val observer : MutableLiveData<EditState> by lazy {
        MutableLiveData<EditState>()
    }

    override val state: LiveData<EditState>
        get() = observer

    override fun updateUser(fullname: String, phone: String, email: String) {
        App.service!!.updateUser(
            fullname,
            phone,
            email
        )
            .map<EditState>(EditState::Result)
            .onErrorReturn(EditState::Error)
            .toFlowable()
            .startWith(EditState.loading)
            .subscribe(observer::postValue)
            .let { return@let App.disposable::add }
    }
}