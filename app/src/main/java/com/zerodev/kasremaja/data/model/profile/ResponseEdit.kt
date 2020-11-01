package com.zerodev.kasremaja.data.model.profile

import com.google.gson.annotations.SerializedName
import com.zerodev.kasremaja.data.model.login.DataLogin

data class ResponseEdit(
    @SerializedName("success") var success: Boolean?,
    @SerializedName("message") var message: String,
    @SerializedName("data") val data : DataEdit?

)