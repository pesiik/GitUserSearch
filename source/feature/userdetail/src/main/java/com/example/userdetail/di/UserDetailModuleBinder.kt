package com.example.userdetail.di

import androidx.lifecycle.ViewModel
import com.example.core.di.annotation.ViewModelKey
import com.example.userdetail.presentation.viewmodel.UserDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class UserDetailModuleBinder {

    @Binds
    @IntoMap
    @ViewModelKey(UserDetailViewModel::class)
    abstract fun createUserDetailViewModel(viewModel: UserDetailViewModel): ViewModel
}