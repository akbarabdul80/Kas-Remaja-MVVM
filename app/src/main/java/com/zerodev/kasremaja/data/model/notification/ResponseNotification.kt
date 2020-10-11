package com.zerodev.kasremaja.data.model.notification

import com.google.gson.annotations.SerializedName

data class ResponseNotification(
    @SerializedName("success") val success : Boolean?,
    @SerializedName("message") val message : String?,
    @SerializedName("notification") val data : List<DataNotification>,

    )