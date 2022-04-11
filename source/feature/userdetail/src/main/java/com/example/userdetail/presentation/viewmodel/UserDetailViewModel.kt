package com.example.userdetail.presentation.viewmodel

import com.example.core.mvvm.BaseViewModel
import com.example.userdetail.data.repository.UserDetailRepository
import com.example.userdetail.presentation.factory.UserDetailResultFactory
import com.example.userdetail.presentation.model.UserDetailResult
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserDetailViewModel @Inject constructor(
    private val username: String,
    private val userDetailRepository: UserDetailRepository,
    private val userDetailResultFactory: UserDetailResultFactory
) : BaseViewModel() {

    private val userDetailMutableState = MutableStateFlow<UserDetailResult>(UserDetailResult.Empty)
    val userDetailState = userDetailMutableState.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        launchWithCancellation(
            {
                userDetailMutableState.emit(UserDetailResult.Empty)
            },
            {
                loadSuccessState()
            },
            { exception ->
                userDetailMutableState.emit(UserDetailResult.Error(exception))
            }
        )
    }

    private suspend fun loadSuccessState() {
        val userDetail = userDetailRepository.getUserByName(username)
        val result = userDetailResultFactory.getUserDetailResult(userDetail)
        userDetailMutableState.emit(result)
    }
}