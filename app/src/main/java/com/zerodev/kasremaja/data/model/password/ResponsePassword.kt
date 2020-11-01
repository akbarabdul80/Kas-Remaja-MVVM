package com.zerodev.kasremaja.data.model.password

import com.google.gson.annotations.SerializedName
import com.zerodev.kasremaja.data.model.notification.DataNotification

data class ResponsePassword(
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : String
)