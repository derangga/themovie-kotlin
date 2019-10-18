package com.themovie.restapi

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
//    private var retrofit: Retrofit? = null
//
//    fun getApiBuilder(): ApiInterface {
//        return retrofit()!!.create(ApiInterface::class.java)
//    }
//
//    private fun retrofit(): Retrofit? {
//
//        val httpLoggingInterceptor = HttpLoggingInterceptor()
//        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        val okHttpClient = OkHttpClient.Builder()
//        okHttpClient.connectTimeout(60, TimeUnit.SECONDS)
//            .writeTimeout(60, TimeUnit.SECONDS)
//            .readTimeout(60, TimeUnit.SECONDS)
//            .addNetworkInterceptor(httpLoggingInterceptor)
//            .build()
//
//        if(retrofit == null){
//            retrofit = Retrofit.Builder()
//                .baseUrl(ApiUrl.BASE_URL)
//                .client(okHttpClient.build())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//        }
//
//        return retrofit
//    }
}