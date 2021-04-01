@file:Suppress("DEPRECATION")

package com.zerodev.kasremaja.ui.dashboard

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexfu.sqlitequerybuilder.api.Column
import com.alexfu.sqlitequerybuilder.api.ColumnConstraint
import com.alexfu.sqlitequerybuilder.api.ColumnType
import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.zerodev.kasremaja.R
import com.zerodev.kasremaja.data.db.Sessions
import com.zerodev.kasremaja.data.model.brosur.DataBrosur
import com.zerodev.kasremaja.root.App
import com.zerodev.kasremaja.root.App.Companion.database
import com.zerodev.kasremaja.ui.history.HistoryActivity
import com.zerodev.kasremaja.ui.notification.NotificationActivity
import com.zerodev.kasremaja.ui.profile.ProfileActivity
import com.zerodev.kasremaja.ui.webview.WebviewActivity
import com.zerodev.kasremaja.utils.Converter
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.bottom_file.view.*
import org.jetbrains.anko.db.delete
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DashboardActivity : AppCompatActivity(),
    DashboardInterfaces {

    lateinit var adapter: DashboardAdapter
    lateinit var viewModel: DashboardViewModel
    var position = 0
    val data: MutableList<DataBrosur> = ArrayList()
    val db = database!!.readableDatabase

    lateinit var dataSelect: DataBrosur

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        when (SimpleDateFormat("HH").format(Date()).toInt()) {
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

        tvUsername.text = App.sessions!!.getString(Sessions.fullname)

        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        viewModel.state.observe(this, { state ->
            state?.let {
                when (it) {
                    is DashboardState.Loading -> {
                        shDashboard.visibility = View.VISIBLE
                        swDashboard.isRefreshing = false
                        avKas.visibility = View.VISIBLE
                        avSaldo.visibility = View.VISIBLE
                        rvBrosur.visibility = View.INVISIBLE
                        tvKas.visibility = View.INVISIBLE
                        tvSaldo.visibility = View.INVISIBLE
                    }
                    is DashboardState.Result -> {
                        shDashboard.visibility = View.INVISIBLE
                        avKas.visibility = View.INVISIBLE
                        avSaldo.visibility = View.INVISIBLE
                        rvBrosur.visibility = View.VISIBLE
                        tvKas.visibility = View.VISIBLE
                        tvSaldo.visibility = View.VISIBLE

                        tvKas.text = Converter.formatRupiah(it.data.saldo)
                        tvSaldo.text = Converter.formatRupiah(it.data.kas)

                        data.clear()

                        //cek file downloaded in sqlite
                        it.data.brosur.forEach {
                            val cursor = db.rawQuery(
                                "SELECT * FROM ${DataBrosur.TABLE_BROSUR} WHERE ${DataBrosur.ID} = ${it.id_brosur}",
                                null
                            )
                            cursor.moveToFirst()

                            it.downloaded = cursor.count == 1

                            data.add(it)

                        }

                        adapter.notifyDataSetChanged()
                    }
                    is DashboardState.Error -> {
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

        btnHistory.setOnClickListener {
            startActivity(Intent(applicationContext, HistoryActivity::class.java))
        }

        swDashboard.setOnRefreshListener {
            viewModel.getData(App.sessions!!.getString(Sessions.id_user))
        }

        btnProfile.setOnClickListener {
            startActivity(Intent(applicationContext, ProfileActivity::class.java))
        }

        SQLiteQueryBuilder.create().table("downloaded")
            .column(Column("id", ColumnType.INTEGER, ColumnConstraint.PRIMARY_KEY))
            .column(Column("id_brosur", ColumnType.INTEGER))
            .build()

        SQLiteQueryBuilder.select()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getData(App.sessions!!.getString(Sessions.id_user))
    }

    private fun askForPermission() {

        val permission: String = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val permissionCam: String = Manifest.permission.CAMERA
        val permissionCard: String = Manifest.permission.ACCESS_FINE_LOCATION
        val requestCode = 101
        val requestCodeCard = 34
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

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(permissionCard),
                    requestCodeCard
                )
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(permission),
                    requestCode
                )

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(permissionCard),
                    requestCodeCard
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

    @SuppressLint("InflateParams", "Recycle")
    override fun onCLick(dataBrosur: DataBrosur, position: Int) {

        this.position = position
        val cursor = db.rawQuery(
            "SELECT * FROM ${DataBrosur.TABLE_BROSUR} WHERE ${DataBrosur.ID} = ${dataBrosur.id_brosur}",
            null
        )
        cursor.moveToFirst()

        if (cursor.count == 1) {
            val file = File(
                Environment.getExternalStorageDirectory().absolutePath + "/" + cursor.getString(
                    5
                )
            )

            if (!file.exists()) {
                App.showToast.toastEror("File sudah dihapus!")
                db.delete(DataBrosur.TABLE_BROSUR, "${DataBrosur.ID} = ${dataBrosur.id_brosur}")

                data[position] = data[position].apply {
                    this.downloaded = false
                }

                adapter.notifyItemChanged(position)

            } else {
                val target = Intent(Intent.ACTION_VIEW)
                val filePath = FileProvider.getUriForFile(
                    this,
                    this.applicationContext.packageName + ".provider",
                    file
                )
                target.setDataAndType(filePath, "application/pdf")
                target.flags =
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                val intent = Intent.createChooser(target, "Open File")
                startActivity(intent)
            }

        } else {

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

                    dataSelect = dataBrosur
                    dialog.dismiss()
                }
            }
            dialog.dismissWithAnimation = true
            dialog.setContentView(menu)
            dialog.show()
        }

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
        registerReceiver(
            onComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
        registerReceiver(
            onNotificationClick,
            IntentFilter(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
        )
        manager.enqueue(request)
        App.showToast.toastDown("Sedang Mendownload")


    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onRestart() {
        super.onRestart()
        when (SimpleDateFormat("HH").format(Date()).toInt()) {
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

        tvUsername.text = App.sessions!!.getString(Sessions.fullname)
    }

    var onComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context, intent: Intent) {

            data[position] = data[position].apply {
                this.downloaded = true
            }
            adapter.notifyItemChanged(position)
            val values = ContentValues()
            values.put(DataBrosur.ID, dataSelect.id_brosur)
            values.put(DataBrosur.TITLE, dataSelect.title_brosur)
            values.put(DataBrosur.URL, dataSelect.url_brosur)
            values.put(DataBrosur.URLVIEW, dataSelect.urlview_brosur)
            values.put(DataBrosur.DOWNLOADED, "TRUE")
            values.put(
                DataBrosur.DIR,
                Environment.DIRECTORY_DOWNLOADS + "/${dataSelect.title_brosur}.pdf"
            )

            db.insert(DataBrosur.TABLE_BROSUR, null, values)

            App.showToast.toastCheck("Download Selesai!")
        }
    }

    var onNotificationClick: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context, intent: Intent) {
            Toast.makeText(ctxt, "Ummmm...hi!", Toast.LENGTH_LONG).show()
        }
    }
}
