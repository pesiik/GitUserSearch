package com.example.userlist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userlist.domain.model.User
import com.example.userlist.domain.repository.UserListRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val DEBOUNCE_PERIOD = 1000L

class UserListViewModel @Inject constructor(
    private val userListRepository: UserListRepository
) : ViewModel() {

    private val userListMutableState = MutableStateFlow<List<User>>(emptyList())
    val userListState = userListMutableState.asStateFlow()

    fun searchUsers(query: String) {
        viewModelScope.launch {
            val users = userListRepository.getUserList(query)
            userListMutableState.emit(users)
        }
    }
}