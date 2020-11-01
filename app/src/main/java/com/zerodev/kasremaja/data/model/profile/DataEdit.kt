package com.zerodev.kasremaja.data.model.profile

import com.google.gson.annotations.SerializedName

data class DataEdit(
    @SerializedName("email") val email : String?,
    @SerializedName("phone") val phone : String?,
    @SerializedName("fullname") val fullname : String?,
)