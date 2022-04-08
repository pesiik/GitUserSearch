package com.example.userdetail.presentation.viewmodel

import com.example.core.mvvm.BaseViewModel
import com.example.userdetail.data.repository.UserDetailRepository
import com.example.userdetail.presentation.model.UserDetailResult
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserDetailViewModel @Inject constructor(
    private val username: String,
    private val userDetailRepository: UserDetailRepository
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
                val userDetail = userDetailRepository.getUserByName(username)
                userDetailMutableState.emit(UserDetailResult.Success(userDetail))
            },
            { exception ->
                userDetailMutableState.emit(UserDetailResult.Error(exception))
            }
        )
    }
}