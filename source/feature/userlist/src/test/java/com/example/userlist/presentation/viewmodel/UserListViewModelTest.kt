package com.example.userlist.presentation.viewmodel

import com.example.userlist.data.repository.UserListRepository
import com.example.userlist.domain.model.User
import com.example.userlist.presentation.model.UserListResult
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class UserListViewModelTest {

    private val userListRepository = mockk<UserListRepository>()
    private lateinit var viewModel: UserListViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        clearAllMocks()
        Dispatchers.setMain(testDispatcher)
        viewModel = UserListViewModel(userListRepository)
    }

    @Test
    fun `should update user list state with users`() = runTest(testDispatcher) {
        val testQuery = "query"
        val testUsers = listOf<User>(mockk())
        coEvery {
            userListRepository.getUserList(testQuery)
        } returns testUsers
        val expectedResult = UserListResult.Success(testUsers)
        viewModel.trySearching(testQuery)
        advanceTimeBy(1001L)
        val actualResult = viewModel.userListState.value
        Assertions.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `should update user list state with error`() = runTest(testDispatcher) {
        val testQuery = "query"
        val testException = mockk<Exception>()
        coEvery {
            userListRepository.getUserList(testQuery)
        } throws testException
        val expectedResult = UserListResult.Error(testException)
        viewModel.trySearching(testQuery)
        advanceTimeBy(1001L)
        val actualResult = viewModel.userListState.value
        Assertions.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `should update user list empty state`() = runTest(testDispatcher) {
        val expectedResult = UserListResult.Empty
        val actualResult = viewModel.userListState.value
        Assertions.assertEquals(expectedResult, actualResult)
    }
}