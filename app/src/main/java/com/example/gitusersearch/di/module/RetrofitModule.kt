package com.example.gitusersearch.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_GIT_URL = "https://api.github.com/"
private const val TOKEN = "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"

@Module
class RetrofitModule {

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().serializeNulls().create()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Accept", "application/json;versions=1")
                    .addHeader("Authorization", TOKEN)
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_GIT_URL)
            .client(okHttpClient).addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}