package com.fleenmobile.androidinterviewtask

import android.app.Activity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast

/**
 * @author FleenMobile at 2018-02-24
 */

fun Activity.showToast(message: String, lenght: Int) =
        Toast.makeText(this, message, lenght).show()

fun View.show() {
    visibility = VISIBLE
}

fun View.hide() {
    visibility = GONE
}