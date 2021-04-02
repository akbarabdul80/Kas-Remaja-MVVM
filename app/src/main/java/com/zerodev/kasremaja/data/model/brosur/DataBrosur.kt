package com.zerodev.kasremaja.data.model.brosur

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataBrosur(
    @SerializedName("id_brosur") val id_brosur : String?,
    @SerializedName("title_brosur") val title_brosur : String?,
    @SerializedName("size_brosur") val size_brosur : String?,
    @SerializedName("date_brosur") val date_brosur : String?,
    @SerializedName("url_brosur") val url_brosur : String?,
    @SerializedName("urlview_brosur") val urlview_brosur : String?,
    @SerializedName("downloaded") var downloaded : Boolean?
) : Parcelable {
    companion object{
        const val TABLE_BROSUR = "table_brosur"
        const val ID = "id_brosur"
        const val TITLE = "title_brosur"
        const val SIZE = "size_brosur"
        const val DATE = "date_brosur"
        const val URL = "url_brosur"
        const val URLVIEW = "urlview_brosur"
        const val DOWNLOADED = "downloaded"
        const val DIR = "dir"
    }
}