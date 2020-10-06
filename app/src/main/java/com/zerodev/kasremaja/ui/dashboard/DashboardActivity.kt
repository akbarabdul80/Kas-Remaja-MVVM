@file:Suppress("DEPRECATION")

package com.zerodev.kasremaja.ui.dashboard

import android.Manifest
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
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.zerodev.kasremaja.R
import com.zerodev.kasremaja.data.model.brosur.DataBrosur
import com.zerodev.kasremaja.ui.notification.NotificationActivity
import com.zerodev.kasremaja.ui.webview.WebviewActivity
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.bottom_file.view.*


class DashboardActivity : AppCompatActivity(),
    DashboardInterfaces {

    lateinit var adapter: DashboardAdapter
    lateinit var viewModel: DashboardViewModel
    val data: MutableList<DataBrosur> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        @Suppress("DEPRECATION")
        viewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)

        data.add(
            0, DataBrosur(
                "1",
                "Hukum Mencuri Bag 3",
                "Size : 2 Mb",
                "Tanggal : 01-02-2002",
                "https://mta.or.id/download/31/download-brosur/1621/201004-hidup-sesudah-mati-bag-10.pdf",
                "https://docs.google.com/viewer?url=https%3A%2F%2Fmta.or.id%2Fwp-admin%2Fadmin-ajax.php%3Fjuwpfisadmin%3Dfalse%26action%3Dwpfd%26task%3Dfile.download%26wpfd_category_id%3D31%26wpfd_file_id%3D1621%26token%3D2913046b504bf439fce67962bd77c712%26preview%3D1&embedded=true"
            )
        )

        data.add(
            0, DataBrosur(
                "2",
                "Hukum Mencuri Bag 2",
                "Size : 3 Mb",
                "Tanggal : 17-01-2002",
                "https://mta.or.id/download/31/download-brosur/1594/200920-hidup-sesudah-mati-bag-8.pdf",
                "https://docs.google.com/viewer?url=https%3A%2F%2Fmta.or.id%2Fwp-admin%2Fadmin-ajax.php%3Fjuwpfisadmin%3Dfalse%26action%3Dwpfd%26task%3Dfile.download%26wpfd_category_id%3D31%26wpfd_file_id%3D1621%26token%3D2913046b504bf439fce67962bd77c712%26preview%3D1&embedded=true"
            )
        )

        data.add(
            0, DataBrosur(
                "2",
                "Hukum Mencuri Bag 1",
                "Size : 4 Mb",
                "Tanggal : 10-01-2002",
                "https://mta.or.id/download/31/download-brosur/1578/200906-hidup-sesudah-mati-bag-6.pdf",
                "https://docs.google.com/viewer?url=https%3A%2F%2Fmta.or.id%2Fwp-admin%2Fadmin-ajax.php%3Fjuwpfisadmin%3Dfalse%26action%3Dwpfd%26task%3Dfile.download%26wpfd_category_id%3D31%26wpfd_file_id%3D1621%26token%3D2913046b504bf439fce67962bd77c712%26preview%3D1&embedded=true"
            )
        )

        data.add(
            0, DataBrosur(
                "3",
                "Tata Cara Sholat Bag 3",
                "Size : 4 Mb",
                "Tanggal : 08-01-2002",
                "https://mta.or.id/download/31/download-brosur/1562/200823-hidup-sesudah-mati-bag-4.pdf",
                "https://docs.google.com/viewer?url=https%3A%2F%2Fmta.or.id%2Fwp-admin%2Fadmin-ajax.php%3Fjuwpfisadmin%3Dfalse%26action%3Dwpfd%26task%3Dfile.download%26wpfd_category_id%3D31%26wpfd_file_id%3D1621%26token%3D2913046b504bf439fce67962bd77c712%26preview%3D1&embedded=true"
            )
        )

        adapter = DashboardAdapter(data, this)
        rvBrosur.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this)
        }

        askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 101)

        btnNotif.setOnClickListener {
            startActivity(Intent(applicationContext, NotificationActivity::class.java))
        }

    }


    private fun askForPermission(permission: String, requestCode: Int) {
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
        val downloadId : Long = manager.enqueue(request)
        Toast.makeText(applicationContext, "Sedang Mendownload", Toast.LENGTH_SHORT).show()
    }
}
