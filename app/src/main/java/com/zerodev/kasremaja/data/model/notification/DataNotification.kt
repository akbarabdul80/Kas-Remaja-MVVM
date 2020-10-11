package com.zerodev.kasremaja.data.model.notification

import com.google.gson.annotations.SerializedName

data class DataNotification(
    @SerializedName("id_notification") val id_notification : String?,
    @SerializedName("title_notification") val title : String?,
    @SerializedName("dec_notification") val dec : String?,
    @SerializedName("date_notification") val date : String?

)