package com.example.userlist.data.mapper

import com.example.userlist.data.model.UserResponse
import com.example.userlist.domain.model.User
import javax.inject.Inject

class UserListMapper @Inject constructor() {

    fun mapUserListResponse(userResponse: UserResponse): List<User> {
        return userResponse.items
            .map { userData ->
                with(userData) {
                    User(login, id, avatarURL, url, score)
                }
            }
    }
}