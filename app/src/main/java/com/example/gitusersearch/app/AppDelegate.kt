package com.example.gitusersearch.app

import android.app.Application
import com.example.gitusersearch.di.component.AppComponent
import com.example.gitusersearch.di.component.AppComponent.Companion.init
import com.example.userlist.di.UserListComponent
import com.example.userlist.di.UserListFeatureProvider

class AppDelegate : Application(), UserListFeatureProvider {

    val appComponent: AppComponent by lazy { init(this) }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

    override fun provideUserListComponent(): UserListComponent {
        return appComponent.userListComponent().build()
    }
}