package com.zerodev.kasremaja.ui.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.zerodev.kasremaja.R
import com.zerodev.kasremaja.data.db.Sessions
import com.zerodev.kasremaja.data.model.notification.DataNotification
import com.zerodev.kasremaja.root.App
import kotlinx.android.synthetic.main.activity_notification.*

class   NotificationActivity :
    AppCompatActivity() {

    lateinit var adapter: NotificationAdapter
    lateinit var viewModel: NotificationViewModel

    val data : MutableList<DataNotification> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        viewModel = ViewModelProvider(this).get(NotificationViewModel::class.java)

        viewModel.state.observe(this, {state ->
            state?.let {
                when(it) {
                    is NotificationState.Loading    -> {
                        shNotificaton.visibility = View.VISIBLE
                        swNotification.isRefreshing = false
                        rvData.visibility = View.GONE
                    }
                    is NotificationState.Result     -> {
                        shNotificaton.visibility = View.INVISIBLE
                        rvData.visibility = View.VISIBLE

                        data.clear()
                        data.addAll(it.data.data)
                        adapter.notifyDataSetChanged()
                    }
                    is NotificationState.Error      -> {
                        App.showToast.toastThrowable(it.error)
                    }
                }
            }

        })

        adapter = NotificationAdapter(data)
        rvData.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this)
        }

        swNotification.setOnRefreshListener {
            viewModel.getData(App.sessions!!.getString(Sessions.id_user))
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getData(App.sessions!!.getString(Sessions.id_user))

    }
}