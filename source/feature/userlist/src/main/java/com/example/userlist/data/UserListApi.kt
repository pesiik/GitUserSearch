package com.example.userlist.data

import com.example.userlist.data.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserListApi {

    @GET("/search/users")
    suspend fun getUserList(
        @Query("q") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): UserResponse
}