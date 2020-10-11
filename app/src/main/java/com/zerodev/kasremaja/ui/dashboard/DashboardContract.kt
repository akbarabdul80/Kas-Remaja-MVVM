package com.zerodev.kasremaja.ui.dashboard

import androidx.lifecycle.LiveData

interface DashboardContract {
    val state: LiveData<DashboardState>

    fun getData(id_user: String)
}