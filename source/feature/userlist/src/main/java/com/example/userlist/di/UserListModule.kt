package com.example.userlist.di

import com.example.userlist.data.UserListApi
import com.example.userlist.domain.mapper.UserListMapper
import com.example.userlist.domain.repository.UserListRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object UserListModule {

    @Provides
    fun provideUserListApi(retrofit: Retrofit): UserListApi {
        return retrofit.create(UserListApi::class.java)
    }

    @Provides
    fun provideUserListRepository(
        userListApi: UserListApi,
        userListMapper: UserListMapper
    ): UserListRepository {
        return UserListRepository(userListApi, userListMapper)
    }
}