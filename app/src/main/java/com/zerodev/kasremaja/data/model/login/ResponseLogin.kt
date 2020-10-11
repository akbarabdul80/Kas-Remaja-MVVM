package com.zerodev.kasremaja.data.model.login

import com.google.gson.annotations.SerializedName

data class ResponseLogin (
    @SerializedName("success") val success : Boolean?,
    @SerializedName("message") val message : String?,
    @SerializedName("data") val data : DataLogin?
)