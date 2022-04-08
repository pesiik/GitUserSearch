package com.example.userdetail.di

import com.example.userdetail.view.fragment.UserDetailFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [UserDetailModule::class, UserDetailModuleBinder::class]
)
interface UserDetailComponent {

    fun inject(userDetailFragment: UserDetailFragment)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun username(username: String): Builder
        fun build(): UserDetailComponent
    }
}