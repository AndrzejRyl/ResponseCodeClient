package com.fleenmobile.androidinterviewtask.api

import com.fleenmobile.androidinterviewtask.api.data.NextPathResponse
import com.fleenmobile.androidinterviewtask.api.data.ResponseCodeResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * @author FleenMobile at 2018-02-24
 */
interface ApiService {

    @GET("./")
    fun nextPath(): Observable<NextPathResponse>

    @GET
    fun responseCode(@Url path: String): Observable<ResponseCodeResponse>
}