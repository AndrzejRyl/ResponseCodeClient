package com.fleenmobile.androidinterviewtask.util

/**
 * @author FleenMobile at 2018-02-24
 */
interface LocalRepository {

    companion object {
        const val RESPONSE_CODE_KEY = "response_code"
        const val FETCH_COUNT_KEY = "fetch_count"

        const val DEFAULT_RESPONSE_CODE = ""
        const val DEFAULT_FETCH_COUNT = 0
    }

    var fetchCount : Int
    var responseCode : String
}