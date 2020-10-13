package com.zerodev.kasremaja.data.network

import com.zerodev.kasremaja.data.model.history.ResponseHistory
import com.zerodev.kasremaja.data.model.kas.ResponseKas
import com.zerodev.kasremaja.data.model.login.ResponseLogin
import com.zerodev.kasremaja.data.model.notification.ResponseNotification
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ApiEndpoint {

    /**
     * Login Api
     */
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ) : Single<ResponseLogin>

    /**
     * Get info kas
     */
    @GET("kas")
    fun kas(
        @Query("id_user") id_user: String
    ): Single<ResponseKas>

    @GET("notification")
    fun notification(
        @Query("id_user") id_user: String
    ): Single<ResponseNotification>

    @GET("history")
    fun history(
    ): Single<ResponseHistory>
}