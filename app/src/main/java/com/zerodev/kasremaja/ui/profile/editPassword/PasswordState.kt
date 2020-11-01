package com.zerodev.kasremaja.ui.profile.editPassword

import com.zerodev.kasremaja.data.model.password.ResponsePassword

sealed class PasswordState{
    object loading: PasswordState()

    data class Result(val data: ResponsePassword) : PasswordState()
    data class Error(val error: Throwable) : PasswordState()
}
