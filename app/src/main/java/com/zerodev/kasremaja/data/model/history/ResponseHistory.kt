package com.zerodev.kasremaja.data.model.history

import com.google.gson.annotations.SerializedName

data class ResponseHistory (
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : String,
    @SerializedName("history") val data : List<DataHistory>
)