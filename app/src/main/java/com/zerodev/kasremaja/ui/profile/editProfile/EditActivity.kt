package com.zerodev.kasremaja.ui.profile.editProfile

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.zerodev.kasremaja.R
import com.zerodev.kasremaja.data.db.Sessions
import com.zerodev.kasremaja.root.App
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    lateinit var viewModel: EditViewModel
    lateinit var pDialog: SweetAlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        viewModel = ViewModelProvider(this).get(EditViewModel::class.java)
        pDialog = SweetAlertDialog(
            this,
            SweetAlertDialog.PROGRESS_TYPE
        )

        et_fullname.setText(App.sessions!!.getString(Sessions.fullname))
        et_email.setText(App.sessions!!.getString(Sessions.email))
        et_phone.setText(App.sessions!!.getString(Sessions.phone))

        viewModel.state.observe(this, { state ->
            state?.let {
                when (it) {
                    is EditState.loading -> {
                        pDialog.progressHelper.barColor = Color.parseColor("#1591F9")
                        pDialog.titleText = "Loading ..."
                        pDialog.setCancelable(false)
                        pDialog.show()
                    }
                    is EditState.Result -> {
                        App.sessions!!.putString(Sessions.email, it.data.data!!.email!!)
                        App.sessions!!.putString(Sessions.phone, it.data.data.phone!!)
                        App.sessions!!.putString(Sessions.fullname, it.data.data.fullname!!)
                        App.showToast.toastCheck(it.data.message)
                        pDialog.dismiss()
                    }
                    is EditState.Error -> {
                        pDialog.dismiss()
                        App.showToast.toastThrowable(it.error)
                    }
                }
            }
        })

        btn_update.setOnClickListener {
            if (
                et_fullname.text.toString() == ""
                && et_email.text.toString() == ""
                && et_phone.text.toString() == ""
            ) {
                App.showToast.toastEror("Silahkan isi semua kolom")
            } else {
                viewModel.updateUser(
                    et_fullname.text.toString(),
                    et_phone.text.toString(),
                    et_email.text.toString()
                )
            }
        }

        btn_back.setOnClickListener {
            finish()
        }
    }
}