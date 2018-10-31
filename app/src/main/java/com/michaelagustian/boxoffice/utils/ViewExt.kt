package com.michaelagustian.boxoffice.utils

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.content.Context
import android.view.View
import android.widget.Toast
import com.michaelagustian.boxoffice.SingleLiveEvent

/**
 * Transforms static java function Toast.makeText() to an extension function on View.
 */
fun View.showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

/**
 * Triggers a toast message when the value contained by toastMessageLiveEvent is modified.
 */
fun View.setupToast(lifecycleOwner: LifecycleOwner, toastMessageLiveEvent: SingleLiveEvent<String>) {
    toastMessageLiveEvent.observe(lifecycleOwner, Observer { toastMessage ->
        toastMessage?.let { showToast(context, it) }
    })
}