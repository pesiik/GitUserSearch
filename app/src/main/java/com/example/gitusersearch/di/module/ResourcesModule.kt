package com.example.gitusersearch.di.module

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides

@Module
class ResourcesModule {

    @Provides
    fun provideResource(context: Context): Resources {
        return context.resources
    }
}