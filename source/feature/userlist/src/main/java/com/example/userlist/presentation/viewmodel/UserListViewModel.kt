package com.example.userlist.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.core.mvvm.SearchableViewModel
import com.example.userlist.data.repository.UserListRepository
import com.example.userlist.domain.model.User
import com.example.userlist.presentation.model.UserListResult
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val EMPTY_QUERY = ""
private const val PER_PAGE_COUNT = 30
private const val START_PAGINATION_STEP = 7
private const val FIRST_PAGE = 1

class UserListViewModel @Inject constructor(
    private val userListRepository: UserListRepository
) : SearchableViewModel() {

    private val userListMutableState = MutableStateFlow<UserListResult>(UserListResult.Clear)
    val userListState = userListMutableState.asStateFlow()

    private val queryMutableState = MutableStateFlow(EMPTY_QUERY)
    val queryState = queryMutableState.asStateFlow()

    private val pageMutableState = MutableStateFlow(FIRST_PAGE)

    init {
        listenToPagination()
    }

    fun trySearchingUsers(query: String) {
        trySearching(query) {
            userListMutableState.emit(UserListResult.Clear)
            searchUsers(query, FIRST_PAGE)
            pageMutableState.emit(FIRST_PAGE)
        }
    }

    fun searchAgain() {
        searchUsers(queryState.value, pageMutableState.value)
    }

    fun updateUsers(lastVisiblePosition: Int) {
        val userResult = userListState.value
        if (userResult is UserListResult.Success) {
            val lastIndex = PER_PAGE_COUNT * pageMutableState.value - START_PAGINATION_STEP
            val hasToPaginate = lastVisiblePosition >= lastIndex
            if (hasToPaginate) {
                pageMutableState.update { currentPage ->
                    currentPage + 1
                }
            }
        }
    }

    private fun searchUsers(query: String, page: Int) {
        launchWithCancellation(
            block = {
                updateUserListState(query, page)
            },
            errorBlock = { exception ->
                userListMutableState.emit(UserListResult.Error(exception))
                updateQueryState(query)
            }
        )
    }

    private fun listenToPagination() {
        viewModelScope.launch {
            pageMutableState.collect { page ->
                val currentQuery = queryMutableState.value
                if (currentQuery.isNotBlank() && currentQuery.isNotEmpty() && page != FIRST_PAGE) {
                    searchUsers(currentQuery, page)
                }
            }
        }
    }

    private suspend fun updateUserListState(query: String, page: Int) {
        val users = userListRepository.getUserList(query, PER_PAGE_COUNT, page)
        val result = if (users.isNotEmpty()) {
            if (userListState.value is UserListResult.Success) {
                val previousResult = userListState.value as UserListResult.Success
                createUpdatedCurrentUserList(previousResult.users, users)
            } else {
                UserListResult.Success(users)
            }
        } else {
            if (userListState.value is UserListResult.Success) {
                UserListResult.Idle
            } else {
                UserListResult.Empty
            }
        }
        userListMutableState.emit(result)
        updateQueryState(query)
    }

    private suspend fun updateQueryState(query: String) {
        queryMutableState.emit(query)
    }

    private fun createUpdatedCurrentUserList(
        previousUserList: List<User>,
        newUserList: List<User>
    ): UserListResult.Success {
        val paginatedList = previousUserList.plus(newUserList)
        return UserListResult.Success(paginatedList)
    }
}