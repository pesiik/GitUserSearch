package com.example.userdetail.data.mapper

import com.example.userdetail.data.model.UserDetailResponse
import com.example.userdetail.domain.UserDetail
import javax.inject.Inject

class UserDetailMapper @Inject constructor() {

    fun mapUserDetailResponse(userDetailResponse: UserDetailResponse): UserDetail {
        return with(userDetailResponse) {
            UserDetail(login, avatarURL, url)
        }
    }
}