@file:Suppress("DEPRECATION")

package com.zerodev.kasremaja.ui.dashboard

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.zerodev.kasremaja.R
import com.zerodev.kasremaja.data.db.Sessions
import com.zerodev.kasremaja.data.model.brosur.DataBrosur
import com.zerodev.kasremaja.root.App
import com.zerodev.kasremaja.ui.notification.NotificationActivity
import com.zerodev.kasremaja.ui.webview.WebviewActivity
import com.zerodev.kasremaja.utils.Converter
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.bottom_file.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DashboardActivity : AppCompatActivity(),
    DashboardInterfaces {

    lateinit var adapter: DashboardAdapter
    lateinit var viewModel: DashboardViewModel
    val data: MutableList<DataBrosur> = ArrayList()

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        when (SimpleDateFormat("hh").format(Date()).toInt()) {
            in 0..11 -> {
                tvSayhello.text = "Selamat Pagi,"
            }
            in 12..14 -> {
                tvSayhello.text = "Selamat Siang,"
            }
            in 15..17 -> {
                tvSayhello.text = "Selamat Sore,"
            }
            else -> {
                tvSayhello.text = "Selamat Malam,"
            }
        }

        tvUsername.text = App.sessions!!.getString(Sessions.username)

        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        viewModel.state.observe(this, { state ->
            state?.let {
                when(it){
                    is DashboardState.Loading   -> {
                        shDashboard.visibility = View.VISIBLE
                        swDashboard.isRefreshing = false
                        avKas.visibility = View.VISIBLE
                        avSaldo.visibility = View.VISIBLE
                        rvBrosur.visibility = View.INVISIBLE
                        tvKas.visibility = View.INVISIBLE
                        tvSaldo.visibility = View.INVISIBLE
                    }
                    is DashboardState.Result    -> {
                        shDashboard.visibility = View.INVISIBLE
                        avKas.visibility = View.INVISIBLE
                        avSaldo.visibility = View.INVISIBLE
                        rvBrosur.visibility = View.VISIBLE
                        tvKas.visibility = View.VISIBLE
                        tvSaldo.visibility = View.VISIBLE

                        tvKas.text = Converter.formatRupiah(it.data.saldo)
                        tvSaldo.text = Converter.formatRupiah(it.data.kas)

                        data.clear()
                        data.addAll(it.data.brosur)
                        adapter.notifyDataSetChanged()
                    }
                    is DashboardState.Error     -> {
                        App.showToast.toastThrowable(it.error)
                    }
                }
            }
        })

        adapter = DashboardAdapter(data, this)
        rvBrosur.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this)
        }

        askForPermission()

        btnNotif.setOnClickListener {
            startActivity(Intent(applicationContext, NotificationActivity::class.java))
        }

        swDashboard.setOnRefreshListener {
            viewModel.getData(App.sessions!!.getString(Sessions.id_user))
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getData(App.sessions!!.getString(Sessions.id_user))
    }

    private fun askForPermission() {

        val permission: String = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val requestCode = 101
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    permission
                )
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(permission),
                    requestCode
                )
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(permission),
                    requestCode
                )
            }
        } else if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            Toast.makeText(applicationContext, "Permission was denied", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("InflateParams")
    override fun onCLick(dataBrosur: DataBrosur) {
        val menu: View = layoutInflater.inflate(R.layout.bottom_file, null)
        val dialog = BottomSheetDialog(
            this,
            R.style.BottomSheetDialogTheme
        )

        menu.also {
            it.btnSee.setOnClickListener {
                seeDocument(dataBrosur.urlview_brosur)
                dialog.dismiss()
            }
            it.btnDownload.setOnClickListener {
                downloadDoc(
                    dataBrosur.url_brosur,
                    dataBrosur.title_brosur
                )
                dialog.dismiss()
            }
        }
        dialog.dismissWithAnimation = true
        dialog.setContentView(menu)
        dialog.show()
    }

    private fun seeDocument(url: String?) {
        val intent = Intent(applicationContext, WebviewActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }

    private fun downloadDoc(url: String?, title: String?) {
        val request = DownloadManager.Request(Uri.parse(url))

        request.setAllowedNetworkTypes(
            DownloadManager.Request.NETWORK_WIFI or
                    DownloadManager.Request.NETWORK_MOBILE
        )

        request.setTitle("Download $title")
        request.setDescription("Lagi download bro..")

        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            "$title.pdf"
        )

        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
        App.showToast.toastDown("Sedang Mendownload")
    }
}
