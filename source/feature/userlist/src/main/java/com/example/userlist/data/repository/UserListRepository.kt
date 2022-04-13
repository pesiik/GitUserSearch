package com.example.userlist.data.repository

import com.example.userlist.data.UserListApi
import com.example.userlist.data.mapper.UserListMapper
import com.example.userlist.domain.model.User
import javax.inject.Inject

class UserListRepository @Inject constructor(
    private val userListApi: UserListApi,
    private val userListMapper: UserListMapper
) {

    suspend fun getUserList(query: String, perPage: Int, page: Int): List<User> {
        val userResponse = userListApi.getUserList(query, perPage, page)
        return userListMapper.mapUserListResponse(userResponse)
    }
}