package com.andreskaminker.iuvocare

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class IuvoCareApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}