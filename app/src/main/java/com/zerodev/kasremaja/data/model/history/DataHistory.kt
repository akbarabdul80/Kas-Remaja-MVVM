package com.zerodev.kasremaja.data.model.history

import com.google.gson.annotations.SerializedName

data class DataHistory(
    @SerializedName("id_history") val id_history : Int,
    @SerializedName("title_history") val title_history : String,
    @SerializedName("dec_history") val dec_history : String,
    @SerializedName("date_history") val date_history : String
)