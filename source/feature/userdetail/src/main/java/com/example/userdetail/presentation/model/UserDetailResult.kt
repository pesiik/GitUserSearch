package com.example.userdetail.presentation.model

import com.example.userdetail.domain.UserDetail

sealed class UserDetailResult {

    data class Success(val userDetail: UserDetail) : UserDetailResult()

    object Empty : UserDetailResult()

    data class Error(val throwable: Throwable) : UserDetailResult()
}