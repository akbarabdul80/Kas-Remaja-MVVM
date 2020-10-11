package com.zerodev.kasremaja.data.model.login

import com.google.gson.annotations.SerializedName

data class DataLogin (
    @SerializedName("id_user") val id_user : String?,
    @SerializedName("username") val username : String?,
    @SerializedName("fullname") val fullname : String?,
    @SerializedName("img_user") val img_user : String?,
    @SerializedName("level") val level : String?
)