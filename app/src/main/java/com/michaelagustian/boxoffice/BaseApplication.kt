package com.michaelagustian.boxoffice

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

/**
 * Created by Michael Agustian on 31/10/18.
 */

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Fresco.initialize(this)
    }
}