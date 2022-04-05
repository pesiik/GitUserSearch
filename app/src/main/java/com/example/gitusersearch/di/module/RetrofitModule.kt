package com.example.gitusersearch.di.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_GIT_URL = "https://api.github.com/"

@Module
class RetrofitModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_GIT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}