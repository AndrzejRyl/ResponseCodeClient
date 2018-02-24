package com.fleenmobile.androidinterviewtask.injection

import android.app.Activity
import android.support.annotation.StringRes

/**
 * @author FleenMobile at 2018-02-24
 */
class ResourceProvider(private val activity: Activity) {

    fun getString(@StringRes id: Int) = activity.resources.getString(id)
}