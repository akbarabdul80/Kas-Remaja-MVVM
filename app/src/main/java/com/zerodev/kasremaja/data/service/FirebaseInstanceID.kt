@file:Suppress("DEPRECATION")

package com.zerodev.kasremaja.data.service

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class FirebaseInstanceID : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token

        Log.e("RegistrationToServer", refreshedToken)
        sendRegistrationToServer(refreshedToken)
    }

    fun sendRegistrationToServer(refreshedToken : String?){

    }
}