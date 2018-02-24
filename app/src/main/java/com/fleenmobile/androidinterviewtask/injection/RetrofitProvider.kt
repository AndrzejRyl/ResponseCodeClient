package com.fleenmobile.androidinterviewtask.injection

import com.fleenmobile.androidinterviewtask.BuildConfig
import com.fleenmobile.androidinterviewtask.api.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author FleenMobile at 2018-02-24
 */
class RetrofitProvider {

    private var retrofitInstance: Retrofit =
            Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(OkHttpClient.Builder().build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

    fun provideApiService(): ApiService = retrofitInstance.create(ApiService::class.java)
}