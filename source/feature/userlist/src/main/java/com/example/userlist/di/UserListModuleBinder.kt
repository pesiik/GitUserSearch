package com.example.userlist.di

import androidx.lifecycle.ViewModel
import com.example.core.di.annotation.ViewModelKey
import com.example.userlist.presentation.viewmodel.UserListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class UserListModuleBinder {

    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModel::class)
    abstract fun createLineChartViewModel(viewModel: UserListViewModel): ViewModel
}