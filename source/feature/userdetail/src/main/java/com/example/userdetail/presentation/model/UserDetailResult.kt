package com.example.userdetail.presentation.model

import com.example.userdetail.view.model.UserDetailBlockModel

sealed class UserDetailResult {

    data class Success(
        val companyModel: UserDetailBlockModel,
        val emailModel: UserDetailBlockModel,
        val locationModel: UserDetailBlockModel,
        val urlModel: UserDetailBlockModel,
        val bio: String
    ) : UserDetailResult()

    object Empty : UserDetailResult()

    data class Error(val throwable: Throwable) : UserDetailResult()
}