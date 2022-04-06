package com.example.userlist.domain.repository

import com.example.userlist.data.UserListApi
import com.example.userlist.domain.mapper.UserListMapper
import com.example.userlist.domain.model.User
import javax.inject.Inject

class UserListRepository @Inject constructor(
    private val userListApi: UserListApi,
    private val userListMapper: UserListMapper
) {

    suspend fun getUserList(query: String): List<User> {
        val userResponse = userListApi.getUserList(query)
        return userListMapper.mapUserListResponse(userResponse)
    }
}