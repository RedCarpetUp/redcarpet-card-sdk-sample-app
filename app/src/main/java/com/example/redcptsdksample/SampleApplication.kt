package com.example.redcptsdksample

import android.app.Application
import com.redcpt.sdk.RedCpt

class SampleApplication : Application() {

    lateinit var sdk: RedCpt
        private set

    override fun onCreate() {
        super.onCreate()
        sdk = RedCpt.init(this, "<API KEY>")
    }

}
