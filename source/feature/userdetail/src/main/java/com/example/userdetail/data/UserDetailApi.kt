package com.example.userdetail.data

import com.example.userdetail.data.model.UserDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UserDetailApi {

    @GET("/users/{username}")
    suspend fun getUser(@Path("username") username: String): UserDetailResponse
}