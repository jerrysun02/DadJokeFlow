package com.langlab.dadjokeflow

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DadJokeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}