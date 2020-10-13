@file:Suppress("DEPRECATION")

package com.zerodev.kasremaja.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.messaging.FirebaseMessaging
import com.zerodev.kasremaja.R
import com.zerodev.kasremaja.data.db.Sessions
import com.zerodev.kasremaja.root.App
import com.zerodev.kasremaja.ui.dashboard.DashboardActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var viewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        setupViewModel()

        btnLogin.setOnClickListener {
            if (
                    etusername.text.toString().isNotBlank() &&
                    etpassword.text.toString().isNotBlank()
            ) {
                viewModel.postLogin(
                        etusername.text.toString(),
                        etpassword.text.toString()
                )
            } else {
                App.showToast.toastEror("Mohon isi email dan password!")
            }

        }
    }

    private fun setupViewModel() {
        viewModel.showMessage.observe(this, { message ->
            message.let {
                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.responseLogin.observe(this, { response ->
            response?.let {
                when(it.success){
                    true -> {
                        App.sessions!!.putString(Sessions.id_user, it.data!!.id_user!!)
                        App.sessions!!.putString(Sessions.username, it.data.username!!)
                        App.sessions!!.putString(Sessions.fullname, it.data.fullname!!)
                        App.sessions!!.putString(Sessions.img_user, it.data.img_user!!)
                        App.sessions!!.putString(Sessions.level, it.data.level!!)

                        FirebaseMessaging.getInstance().subscribeToTopic(it.data.id_user.toString())

                        startActivity(Intent(applicationContext, DashboardActivity::class.java))
                        App.showToast.toastCheck(it.message!!)
                    }
                    false -> {
                        App.showToast.toastEror(it.message!!)
                    }
                }
            }

        })
    }
}