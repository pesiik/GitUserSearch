package com.example.userlist.di

import com.example.userlist.view.fragment.UserListFragment
import dagger.Subcomponent

@Subcomponent(
    modules = [UserListModule::class, UserListModuleBinder::class]
)
interface UserListComponent {

    fun inject(userListFragment: UserListFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): UserListComponent
    }
}