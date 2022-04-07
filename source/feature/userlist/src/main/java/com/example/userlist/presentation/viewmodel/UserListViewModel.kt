package com.example.userlist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userlist.data.repository.UserListRepository
import com.example.userlist.presentation.model.UserListResult
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val DEBOUNCE_PERIOD = 1000L

class UserListViewModel @Inject constructor(
    private val userListRepository: UserListRepository
) : ViewModel() {

    private val userListMutableState = MutableStateFlow<UserListResult>(UserListResult.Empty)
    val userListState = userListMutableState.asStateFlow()

    private var searchJob: Job? = null

    fun trySearching(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(DEBOUNCE_PERIOD)
            searchUsers(query)
        }
    }

    private fun searchUsers(query: String) { //todo вынести в базовую
        viewModelScope.launch {
            userListMutableState.emit(UserListResult.Empty)
            try {
                val users = userListRepository.getUserList(query)
                val result = UserListResult.Success(users)
                userListMutableState.emit(result)
            } catch (exception: Exception) {
                if (exception is CancellationException) {
                    throw exception
                }
                userListMutableState.emit(UserListResult.Error(exception))
            }
        }
    }
}