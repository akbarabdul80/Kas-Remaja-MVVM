package com.zerodev.kasremaja.utils

import android.content.Context
import android.widget.Toast
import com.zerodev.kasremaja.R
import com.muddzdev.styleabletoast.StyleableToast
import com.zerodev.kasremaja.BuildConfig
import com.zerodev.kasremaja.root.App
import retrofit2.HttpException

class MakeToast(val context: Context) {

    fun toastDown(message: String) {
        StyleableToast.makeText(this.context, message, Toast.LENGTH_SHORT, R.style.toastdownload).show()
    }

    fun toastCheck(message: String) {
        StyleableToast.makeText(this.context, message, Toast.LENGTH_SHORT, R.style.toastcheck).show()
    }

    fun toastEror(message: String) {
        StyleableToast.makeText(this.context, message, Toast.LENGTH_SHORT, R.style.toasterror).show()
    }

    fun toastThrowable(error: Throwable) {
        if (error is HttpException) {
            when (error.code()) {
                400 -> App.showToast.toastEror("Gagal memuat data! : " +
                        "${error.code()} !")
                404 -> App.showToast.toastEror("Url yang diminta tidak ditemukan! : " +
                        "${error.code()} !")
                500 -> App.showToast.toastEror("Server sedang gangguan! : " +
                        "${error.code()} !")
                else -> if (BuildConfig.DEBUG)
                    App.showToast.toastEror("Gagal memuat data! : " +
                            "${error.message()} !")
            }
        } else {
            if (BuildConfig.DEBUG)
                App.showToast.toastEror("Gagal memuat data! : " +
                        "${error.message} !")
        }
    }
}