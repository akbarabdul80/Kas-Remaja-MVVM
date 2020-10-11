package com.zerodev.kasremaja.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.zerodev.kasremaja.R
import com.zerodev.kasremaja.root.App
import com.zerodev.kasremaja.ui.dashboard.DashboardActivity
import com.zerodev.kasremaja.ui.login.LoginActivity

class SplaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splace)

        Handler().postDelayed({
            if (App.sessions!!.isLogin()){
                startActivity(Intent(applicationContext, DashboardActivity::class.java))
            }else{
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            }
            finish()
        }, 2000L)
    }
}