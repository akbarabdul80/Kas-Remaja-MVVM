package com.zerodev.kasremaja.root

import android.app.Application
import com.zerodev.kasremaja.data.db.Sessions
import com.zerodev.kasremaja.data.network.ApiService
import com.zerodev.kasremaja.utils.MakeToast
import io.reactivex.disposables.CompositeDisposable

class App : Application() {

    companion object{
        var service : ApiService?= null
        lateinit var disposable : CompositeDisposable
        lateinit var showToast: MakeToast
        var sessions : Sessions?= null
    }

    override fun onCreate() {
        super.onCreate()

        service = ApiService()
        disposable = CompositeDisposable()
        sessions = Sessions(this)
        showToast = MakeToast(this)
    }

}