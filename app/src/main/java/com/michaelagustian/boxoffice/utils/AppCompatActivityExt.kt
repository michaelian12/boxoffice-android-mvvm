package com.michaelagustian.boxoffice.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

fun AppCompatActivity.setupActionBar(toolbar: Toolbar, needHomeButton: Boolean = false) {
    setSupportActionBar(toolbar)
    supportActionBar?.let {
        it.apply{
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(needHomeButton)
            setDisplayShowTitleEnabled(false)
        }
    }
}

/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, frameId: Int) {
    supportFragmentManager.transact{
        replace(frameId, fragment)
    }
}

/**
 * Runs a FragmentTransaction, then calls commit().
 */
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}