package com.zerodev.kasremaja.ui.profile.editProfile

import androidx.lifecycle.LiveData
import com.zerodev.kasremaja.ui.notification.NotificationState

interface EditContract {
    val state: LiveData<EditState>

    fun updateUser(fullname: String, phone: String, email: String)
}