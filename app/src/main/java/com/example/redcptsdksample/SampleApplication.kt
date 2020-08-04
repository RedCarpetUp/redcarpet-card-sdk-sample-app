package com.example.redcptsdksample

import android.app.Application
import com.redcpt.sdk.RedCpt

class SampleApplication : Application() {

    lateinit var sdk: RedCpt
        private set

    override fun onCreate() {
        super.onCreate()
        sdk = RedCpt.init(this, "4d0de8ca9539459fb3a3b1d56b1f6f19")
    }

}