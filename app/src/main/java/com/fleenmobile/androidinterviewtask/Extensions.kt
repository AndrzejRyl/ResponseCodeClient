package com.fleenmobile.androidinterviewtask

import android.app.Activity
import android.widget.Toast

/**
 * @author FleenMobile at 2018-02-24
 */

fun Activity.showToast(message: String, lenght: Int) =
        Toast.makeText(this, message, lenght).show()