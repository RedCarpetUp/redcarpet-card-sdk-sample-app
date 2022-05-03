package com.redcarpetup.sdk.android.example

import android.app.Application
import com.redcarpetup.sdk.android.RedCarpetUpSdk

class SampleApplication  : Application() {
    override fun onCreate() {
        super.onCreate()
        RedCarpetUpSdk.initialize(this)    }


}