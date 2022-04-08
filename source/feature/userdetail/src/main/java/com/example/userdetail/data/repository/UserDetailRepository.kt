package com.example.userdetail.data.repository

import com.example.userdetail.data.UserDetailApi
import com.example.userdetail.data.mapper.UserDetailMapper
import com.example.userdetail.domain.UserDetail
import javax.inject.Inject

class UserDetailRepository @Inject constructor(
    private val userDetailApi: UserDetailApi,
    private val userDetailMapper: UserDetailMapper
) {

    suspend fun getUserByName(username: String): UserDetail {
        val userResponse = userDetailApi.getUser(username)
        return userDetailMapper.mapUserDetailResponse(userResponse)
    }
}