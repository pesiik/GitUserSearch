package com.example.gitusersearch.app

import android.app.Application
import com.example.gitusersearch.di.component.AppComponent
import com.example.gitusersearch.di.component.AppComponent.Companion.init

class AppDelegate : Application() {

    val appComponent: AppComponent by lazy { init(this) }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }
}