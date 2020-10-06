package com.zerodev.kasremaja.data.network

import com.zerodev.kasremaja.data.model.login.ResponseLogin
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ApiEndpoint {

    /**
     * Login
     */

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ) : Single<ResponseLogin>


    // option 2: using a dynamic URL
    @GET
    fun downloadFileWithDynamicUrl(@Url fileUrl: String?): Call<ResponseBody?>?
}