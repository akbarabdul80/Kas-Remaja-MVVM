package com.zerodev.kasremaja.ui.profile.editPassword

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.zerodev.kasremaja.R
import com.zerodev.kasremaja.root.App
import com.zerodev.kasremaja.ui.profile.editProfile.EditState
import kotlinx.android.synthetic.main.activity_password.*

class PasswordActivity : AppCompatActivity() {

    lateinit var viewModel: PasswordViewModel
    lateinit var pDialog: SweetAlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        viewModel = ViewModelProvider(this).get(PasswordViewModel::class.java)
        pDialog = SweetAlertDialog(
            this,
            SweetAlertDialog.PROGRESS_TYPE
        )

        viewModel.state.observe(this, { state ->
            state?.let {
                when (it) {
                    is PasswordState.loading -> {
                        pDialog.progressHelper.barColor = Color.parseColor("#1591F9")
                        pDialog.titleText = "Loading ..."
                        pDialog.setCancelable(false)
                        pDialog.show()
                    }
                    is PasswordState.Result -> {
                        if (it.data.success){
                            App.showToast.toastCheck(it.data.message)
                            pDialog.dismiss()
                        }else{
                            App.showToast.toastEror(it.data.message)
                            pDialog.dismiss()
                        }

                    }
                    is PasswordState.Error -> {
                        pDialog.dismiss()
                        App.showToast.toastThrowable(it.error)
                    }
                }
            }
        })

        btn_update.setOnClickListener {
            if (
                et_oldpass.text.toString() == ""
                && et_newpass.text.toString() == ""
                && et_repass.text.toString() == ""
            ) {
                App.showToast.toastEror("Silahkan ini semua kolom")
            } else if (
                et_newpass.text.toString()
                != et_repass.text.toString()
            ) {
                App.showToast.toastEror("Password tidak sama")
            } else {
                viewModel.updatePass(
                    et_oldpass.text.toString(),
                    et_newpass.text.toString(),
                    et_repass.text.toString()
                )
            }
        }

        btn_back.setOnClickListener {
            finish()
        }

    }
}