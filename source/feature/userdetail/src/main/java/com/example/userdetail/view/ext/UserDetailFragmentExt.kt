package com.example.userdetail.view.ext

import com.example.userdetail.di.UserDetailComponent
import com.example.userdetail.di.UserDetailFeatureProvider
import com.example.userdetail.view.fragment.UserDetailFragment

fun UserDetailFragment.inject(username: String) {
    val component = getUserDetailComponent()
    component
        .username(username)
        .build()
        .inject(this)
}

private fun UserDetailFragment.getUserDetailComponent(): UserDetailComponent.Builder {
    return (requireActivity().applicationContext as UserDetailFeatureProvider).provideUserDetailComponentBuilder()
}