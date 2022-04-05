package com.example.gitusersearch.di.component

import com.example.gitusersearch.app.AppDelegate
import com.example.gitusersearch.di.module.ContextModule
import com.example.gitusersearch.di.module.RetrofitModule
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        ContextModule::class,
        RetrofitModule::class
    ]
)
abstract class AppComponent {
    companion object {
        fun init(appDelegate: AppDelegate): AppComponent {
            return DaggerAppComponent.builder().appDelegate(appDelegate).build()
        }
    }

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun appDelegate(appDelegate: AppDelegate): Builder

        fun build(): AppComponent
    }

    abstract fun inject(appDelegate: AppDelegate)
}