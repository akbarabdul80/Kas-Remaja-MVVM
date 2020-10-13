package com.zerodev.kasremaja.data.network

import com.zerodev.kasremaja.BuildConfig
import com.zerodev.kasremaja.data.model.history.ResponseHistory
import com.zerodev.kasremaja.data.model.kas.ResponseKas
import com.zerodev.kasremaja.data.model.login.ResponseLogin
import com.zerodev.kasremaja.data.model.notification.ResponseNotification
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
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
            .addInterceptor( Interceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.header("X-API-KEY", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9")
                return@Interceptor chain.proceed(builder.build())
            })
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()


        val server = "http://192.168.43.73/kas/api/"
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

    fun getKas(id_user : String) : Single<ResponseKas> {
        return api.kas(id_user)
    }

    fun getNotification(id_user: String) : Single<ResponseNotification>{
        return api.notification(id_user)
    }

    fun getHistory() : Single<ResponseHistory>{
        return api.history()
    }
}