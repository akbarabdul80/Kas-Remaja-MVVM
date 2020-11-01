package com.zerodev.kasremaja.ui.profile.editProfile

import com.zerodev.kasremaja.data.model.profile.ResponseEdit

sealed class EditState {
    object loading : EditState()

    data class Result(val data: ResponseEdit) : EditState()
    data class Error(val error: Throwable) : EditState()
}