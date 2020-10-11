@file:Suppress("DEPRECATION")

package com.zerodev.kasremaja.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
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
                Toast.makeText(applicationContext, "Mohon isi email dan password!", Toast.LENGTH_SHORT).show()
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

                        startActivity(Intent(applicationContext, DashboardActivity::class.java))
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()

                    }
                    false -> {
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }
}