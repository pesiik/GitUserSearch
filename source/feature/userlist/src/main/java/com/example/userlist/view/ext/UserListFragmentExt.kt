package com.example.userlist.view.ext

import com.example.userlist.di.UserListComponent
import com.example.userlist.di.UserListFeatureProvider
import com.example.userlist.view.UserListFragment

fun UserListFragment.inject() {
    val component = getUserListComponent()
    component.inject(this)
}

private fun UserListFragment.getUserListComponent(): UserListComponent {
    return (requireActivity().applicationContext as UserListFeatureProvider).provideUserListComponent()
}