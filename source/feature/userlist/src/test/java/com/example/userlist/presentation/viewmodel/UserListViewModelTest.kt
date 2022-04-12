package com.example.userlist.presentation.viewmodel

import com.example.userlist.data.repository.UserListRepository
import com.example.userlist.domain.model.User
import com.example.userlist.presentation.model.UserListResult
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
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
    fun `should update user list state with users by try again`() = runTest(testDispatcher) {
        val testQuery = "query"
        val testUsers = listOf<User>(mockk())
        coEvery {
            userListRepository.getUserList(testQuery)
        } returns testUsers
        viewModel.trySearching(testQuery)
        advanceTimeBy(1001L)
        viewModel.searchAgain()
        advanceTimeBy(1000L)
        coVerify(exactly = 2) {
            userListRepository.getUserList(testQuery)
        }
    }

    @Test
    fun `should not update user list state with users if query is empty`() = runTest(testDispatcher) {
        val testQuery = ""
        val testUsers = listOf<User>(mockk())
        coEvery {
            userListRepository.getUserList(testQuery)
        } returns testUsers
        viewModel.trySearching(testQuery)
        advanceTimeBy(1001L)
        coVerify(exactly = 0) {
            userListRepository.getUserList(testQuery)
        }
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
        val testQuery = "query"
        coEvery {
            userListRepository.getUserList(testQuery)
        } returns emptyList()
        val expectedResult = UserListResult.Empty
        viewModel.trySearching(testQuery)
        advanceTimeBy(1001L)
        val actualResult = viewModel.userListState.value
        Assertions.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `should update user list idle state`() = runTest(testDispatcher) {
        val expectedResult = UserListResult.Idle
        val actualResult = viewModel.userListState.value
        Assertions.assertEquals(expectedResult, actualResult)
    }
}