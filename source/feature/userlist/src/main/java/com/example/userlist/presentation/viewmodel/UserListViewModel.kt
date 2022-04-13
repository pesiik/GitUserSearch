package com.example.userlist.presentation.viewmodel

import com.example.core.mvvm.SearchableViewModel
import com.example.userlist.data.repository.UserListRepository
import com.example.userlist.presentation.model.UserListResult
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val EMPTY_QUERY = ""

class UserListViewModel @Inject constructor(
    private val userListRepository: UserListRepository
) : SearchableViewModel() {

    private val userListMutableState = MutableStateFlow<UserListResult>(UserListResult.Idle)
    val userListState = userListMutableState.asStateFlow()

    private val queryMutableState = MutableStateFlow(EMPTY_QUERY)
    val queryState = queryMutableState.asStateFlow()

    fun trySearchingUsers(query: String) {
        trySearching(query, (::searchUsers))
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