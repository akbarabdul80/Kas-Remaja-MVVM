package com.zerodev.kasremaja.data.network

import com.zerodev.kasremaja.BuildConfig
import com.zerodev.kasremaja.data.model.login.ResponseLogin
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiService {
    private val api : ApiEndpoint

    init {
        val client = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else
                    HttpLoggingInterceptor.Level.NONE
            })
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        val server = "https://sedekahkusragen.000webhostapp.com/app/"
        api = Retrofit.Builder()
            .baseUrl(server)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .build()
            .create(ApiEndpoint::class.java)
    }

    fun postLogin(username : String, password : String) : Single<ResponseLogin> {
        return api.login(username, password)
    }
}