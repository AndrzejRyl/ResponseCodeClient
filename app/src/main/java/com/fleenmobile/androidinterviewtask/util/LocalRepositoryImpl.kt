package com.fleenmobile.androidinterviewtask.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.fleenmobile.androidinterviewtask.util.LocalRepository.Companion.DEFAULT_FETCH_COUNT
import com.fleenmobile.androidinterviewtask.util.LocalRepository.Companion.DEFAULT_RESPONSE_CODE
import com.fleenmobile.androidinterviewtask.util.LocalRepository.Companion.FETCH_COUNT_KEY
import com.fleenmobile.androidinterviewtask.util.LocalRepository.Companion.RESPONSE_CODE_KEY

/**
 * @author FleenMobile at 2018-02-24
 */
class LocalRepositoryImpl(context: Context) : LocalRepository {

    private val preferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

    override var fetchCount: Int
        get() = preferences.getInt(FETCH_COUNT_KEY, DEFAULT_FETCH_COUNT)
        set(count) = preferences
                .edit()
                .putInt(FETCH_COUNT_KEY, count)
                .apply()

    override var responseCode: String
        get() = preferences.getString(RESPONSE_CODE_KEY, DEFAULT_RESPONSE_CODE)
        set(code) = preferences
                .edit()
                .putString(RESPONSE_CODE_KEY, code)
                .apply()
}