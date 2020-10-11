package com.zerodev.kasremaja.ui.notification

import com.zerodev.kasremaja.data.model.kas.ResponseKas
import com.zerodev.kasremaja.data.model.notification.ResponseNotification
import com.zerodev.kasremaja.ui.dashboard.DashboardState

sealed class NotificationState {
    object Loading : NotificationState()

    data class Result(val data : ResponseNotification) : NotificationState()
    data class Error(val error : Throwable) : NotificationState()
}