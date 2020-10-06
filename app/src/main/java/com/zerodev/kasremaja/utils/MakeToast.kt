package com.zerodev.kasremaja.utils

import android.content.Context
import android.widget.Toast

class MakeToast {
    companion object{
        var context : Context?= null

        fun ShowToast(message : String) {
            Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
        }
    }


}