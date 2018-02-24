package com.fleenmobile.androidinterviewtask.api.data

import com.google.gson.annotations.SerializedName

/**
 * @author FleenMobile at 2018-02-24
 */
data class ResponseCodeResponse(val path: String, @SerializedName("response_code") val responseCode: String)