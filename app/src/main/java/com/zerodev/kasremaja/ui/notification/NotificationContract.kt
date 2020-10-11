package com.zerodev.kasremaja.ui.notification

import androidx.lifecycle.LiveData

interface NotificationContract {
    val state: LiveData<NotificationState>

    fun getData(id_user: String)
}