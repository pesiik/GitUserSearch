package com.example.gitusersearch.di.module

import android.content.Context
import com.example.gitusersearch.app.AppDelegate
import dagger.Binds
import dagger.Module

@Module
abstract class ContextModule {

    @Binds
    abstract fun bindApplicationContext(appDelegate: AppDelegate): Context
}