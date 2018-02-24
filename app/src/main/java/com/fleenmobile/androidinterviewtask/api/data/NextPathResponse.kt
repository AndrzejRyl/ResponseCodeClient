package com.fleenmobile.androidinterviewtask.api.data

import com.google.gson.annotations.SerializedName

/**
 * @author FleenMobile at 2018-02-24
 */
data class NextPathResponse(@SerializedName("next_path") val path: String)