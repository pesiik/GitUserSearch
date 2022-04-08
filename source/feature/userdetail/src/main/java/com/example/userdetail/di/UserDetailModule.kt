package com.example.userdetail.di

import com.example.userdetail.data.UserDetailApi
import com.example.userdetail.data.mapper.UserDetailMapper
import com.example.userdetail.data.repository.UserDetailRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object UserDetailModule {

    @Provides
    fun provideUserDetailApi(retrofit: Retrofit): UserDetailApi {
        return retrofit.create(UserDetailApi::class.java)
    }

    @Provides
    fun provideUserDetailRepository(
        userDetailApi: UserDetailApi,
        userDetailMapper: UserDetailMapper
    ): UserDetailRepository {
        return UserDetailRepository(userDetailApi, userDetailMapper)
    }
}