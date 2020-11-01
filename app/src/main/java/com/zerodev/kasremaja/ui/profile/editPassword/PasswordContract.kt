package com.zerodev.kasremaja.ui.profile.editPassword

import androidx.lifecycle.LiveData

interface PasswordContract {
    val state: LiveData<PasswordState>

    fun updatePass(oldPass: String, newPass: String, rePass: String)
}