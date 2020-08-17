package com.andreskaminker.iuvohelp

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class IuvoCareApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}