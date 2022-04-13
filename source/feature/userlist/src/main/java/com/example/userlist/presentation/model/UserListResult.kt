package com.example.userlist.presentation.model

import com.example.userlist.domain.model.User

sealed class UserListResult {

    data class Success(val users: List<User>) : UserListResult()

    object Idle : UserListResult()

    object Clear : UserListResult()

    object Empty : UserListResult()

    data class Error(val throwable: Throwable) : UserListResult()
}
