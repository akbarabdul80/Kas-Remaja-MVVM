package com.zerodev.kasremaja.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.zerodev.kasremaja.R
import com.zerodev.kasremaja.data.db.Sessions
import com.zerodev.kasremaja.root.App
import com.zerodev.kasremaja.ui.login.LoginActivity
import com.zerodev.kasremaja.ui.profile.editPassword.PasswordActivity
import com.zerodev.kasremaja.ui.profile.editProfile.EditActivity
import com.zerodev.kasremaja.utils.ImageHelper
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        ImageHelper.getPicasso(
            imgprofil,
            App.server + "upload/" + App.sessions!!.getString(Sessions.img_user)
        )

        tv_fullname.text = App.sessions!!.getString(Sessions.fullname)
        tv_email.text = App.sessions!!.getString(Sessions.email)
        tv_phone.text = App.sessions!!.getString(Sessions.phone)

        btn_changeProfile.setOnClickListener {
            startActivity(Intent(applicationContext, EditActivity::class.java))
        }

        btn_changePass.setOnClickListener{
            startActivity(Intent(applicationContext, PasswordActivity::class.java))
        }

        btnLogout.setOnClickListener{
            SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Anda akan Logout?")
                .setConfirmText("Logout!")
                .setConfirmClickListener { sweetAlertDialog: SweetAlertDialog? ->
                    App.sessions!!.Logout()
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                    finish()
                }
                .setCancelButton("Cancel") { obj: SweetAlertDialog -> obj.dismissWithAnimation() }
                .show()
        }
    }

    override fun onRestart() {
        super.onRestart()
        tv_fullname.text = App.sessions!!.getString(Sessions.fullname)
        tv_email.text = App.sessions!!.getString(Sessions.email)
        tv_phone.text = App.sessions!!.getString(Sessions.phone)
    }
}