package com.example.gitusersearch.di.component

import com.example.gitusersearch.app.AppDelegate
import com.example.gitusersearch.di.module.ContextModule
import com.example.gitusersearch.di.module.ResourcesModule
import com.example.gitusersearch.di.module.RetrofitModule
import com.example.userdetail.di.UserDetailComponent
import com.example.userlist.di.UserListComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ContextModule::class,
        RetrofitModule::class,
        SubcomponentModules::class,
        ResourcesModule::class
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

    abstract fun userListComponent(): UserListComponent.Builder

    abstract fun userDetailComponent(): UserDetailComponent.Builder
}