package com.zerodev.kasremaja.ui.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.zerodev.kasremaja.R
import com.zerodev.kasremaja.data.model.notification.DataNotification
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : AppCompatActivity() {

    lateinit var adapter: NotificationAdapter
    val data : MutableList<DataNotification> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        data.add(
            DataNotification(
            "1",
            "Pemabayaran",
            "Pembayaran kas sebesar Rp.3.000",
                "Tanggal : 20-12-2020"
        )
        )

        data.add(DataNotification(
            "1",
            "Pemabayaran",
            "Pembayaran kas sebesar Rp.3.000",
            "Tanggal : 20-12-2020"
        ))

        data.add(DataNotification(
            "1",
            "Pemabayaran",
            "Pembayaran kas sebesar Rp.3.000",
            "Tanggal : 20-12-2020"
        ))

        data.add(DataNotification(
            "1",
            "Pemabayaran",
            "Pembayaran kas sebesar Rp.3.000",
            "Tanggal : 20-12-2020"
        ))

        adapter = NotificationAdapter(data)
        rvData.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this)
        }

    }
}