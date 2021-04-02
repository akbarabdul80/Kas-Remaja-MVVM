package com.zerodev.kasremaja.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.zerodev.kasremaja.R
import com.zerodev.kasremaja.root.App
import com.zerodev.kasremaja.ui.dashboard.DashboardActivity
import com.zerodev.kasremaja.ui.login.LoginActivity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress


@Suppress("DEPRECATION")
class SplaceActivity : AppCompatActivity() {
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splace)


        Handler().postDelayed({
            if (App.sessions!!.isLogin()) {
                hasInternetConnection().subscribe { hasInternet ->
                    run {
                        if (hasInternet) {
                            val intent = Intent(applicationContext, DashboardActivity::class.java)
                            intent.putExtra("mode", true)
                            startActivity(intent)
                        } else {
                            App.showToast.toastEror("Anda tidak memiliki koneksi internet!, Mode Offline diaktifkan!")
                            val intent = Intent(applicationContext, DashboardActivity::class.java)
                            intent.putExtra("mode", false)
                            startActivity(intent)
                        }
                    }
                }
            } else {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            }
            finish()
        }, 2000L)

    }

    fun hasInternetConnection(): Single<Boolean> {
        return Single.fromCallable {
            try {
                // Connect to Google DNS to check for connection
                val timeoutMs = 1500
                val socket = Socket()
                val socketAddress = InetSocketAddress("8.8.8.8", 53)

                socket.connect(socketAddress, timeoutMs)
                socket.close()

                true
            } catch (e: IOException) {
                false
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun isInternetAvailable(): Boolean {
        return try {
            val timeoutMs = 1500
            val sock = Socket()
            val sockaddr: SocketAddress = InetSocketAddress("8.8.8.8", 53)
            sock.connect(sockaddr, timeoutMs)
            sock.close()
            true
        } catch (e: IOException) {
            false
        }
    }

    fun isConnected(): Boolean {
        var connected = false
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.allNetworkInfo
        for (ni in netInfo) {
            if ((ni.typeName.equals("WIFI", ignoreCase = true)
                        || ni.typeName.equals("MOBILE", ignoreCase = true))
                && ni.isConnected && ni.isAvailable
            ) {
                connected = true
            }
        }
        return connected
    }
}