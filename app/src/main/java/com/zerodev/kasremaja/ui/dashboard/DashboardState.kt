package com.zerodev.kasremaja.ui.dashboard

import com.zerodev.kasremaja.data.model.kas.ResponseKas

sealed class DashboardState {
    object Loading : DashboardState()

    data class Result(val data : ResponseKas) : DashboardState()
    data class Error(val error : Throwable) : DashboardState()
}