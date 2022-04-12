package com.example.userlist.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.core.mvvm.BaseViewModel
import com.example.userlist.data.repository.UserListRepository
import com.example.userlist.presentation.model.UserListResult
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val DEBOUNCE_PERIOD = 1000L
private const val EMPTY_QUERY = ""

class UserListViewModel @Inject constructor(
    private val userListRepository: UserListRepository
) : BaseViewModel() {

    private val userListMutableState = MutableStateFlow<UserListResult>(UserListResult.Idle)
    val userListState = userListMutableState.asStateFlow()

    private val queryMutableState = MutableStateFlow(EMPTY_QUERY)
    val queryState = queryMutableState.asStateFlow()

    private var searchJob: Job? = null

    fun trySearching(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(DEBOUNCE_PERIOD)
            if (query.isNotEmpty()) {
                searchUsers(query)
            }
        }
    }

    fun searchAgain() {
        searchUsers(queryState.value)
    }

    private fun searchUsers(query: String) {
        launchWithCancellation(
            {
                userListMutableState.emit(UserListResult.Idle)
            },
            {
                updateUseListState(query)
            },
            { exception ->
                userListMutableState.emit(UserListResult.Error(exception))
                updateQueryState(query)
            }
        )
    }

    private suspend fun updateUseListState(query: String) {
        val users = userListRepository.getUserList(query)
        val result = if (users.isNotEmpty()) {
            UserListResult.Success(users)
        } else {
            UserListResult.Empty
        }
        userListMutableState.emit(result)
        updateQueryState(query)
    }

    private suspend fun updateQueryState(query: String) {
        queryMutableState.emit(query)
    }
}