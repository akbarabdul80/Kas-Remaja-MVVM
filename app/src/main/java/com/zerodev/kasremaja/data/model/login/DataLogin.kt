package com.zerodev.kasremaja.data.model.login

import com.google.gson.annotations.SerializedName

data class DataLogin (
    @SerializedName("id") val id_user : String?,
    @SerializedName("username") val username : String?,
    @SerializedName("email") val email : String?,
    @SerializedName("token") val token : String?
)