package com.zerodev.kasremaja.data.model.kas

import com.google.gson.annotations.SerializedName
import com.zerodev.kasremaja.data.model.brosur.DataBrosur

data class ResponseKas (
    @SerializedName("success") val success : Boolean?,
    @SerializedName("message") val message : String?,
    @SerializedName("saldo") val saldo: Int,
    @SerializedName("kas_saldo") val kas: Int,
    @SerializedName("brosur") val brosur: List<DataBrosur>
)