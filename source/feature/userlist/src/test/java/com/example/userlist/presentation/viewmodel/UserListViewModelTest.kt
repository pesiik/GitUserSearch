package com.example.userlist.presentation.viewmodel

import com.example.userlist.data.repository.UserListRepository
import com.example.userlist.domain.model.User
import com.example.userlist.presentation.model.UserListResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
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
    private val testPerPage = 30
    private val testPage = 1

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = UserListViewModel(userListRepository)
    }

    @Test
    fun `should update user list state with users`() = runTest(testDispatcher) {
        val testQuery = "query"
        val testUsers = listOf<User>(mockk())
        coEvery {
            userListRepository.getUserList(testQuery, testPerPage, testPage)
        } returns testUsers
        val expectedResult = UserListResult.Success(testUsers)
        viewModel.trySearchingUsers(testQuery)
        advanceUntilIdle()
        val actualResult = viewModel.userListState.value
        Assertions.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `should update user list state with users by try again`() = runTest(testDispatcher) {
        val testQuery = "query"
        val testUsers = listOf<User>(mockk())
        coEvery {
            userListRepository.getUserList(testQuery, testPerPage, testPage)
        } returns testUsers
        viewModel.trySearchingUsers(testQuery)
        advanceUntilIdle()
        viewModel.searchAgain()
        advanceUntilIdle()
        coVerify(exactly = 2) {
            userListRepository.getUserList(testQuery, testPerPage, testPage)
        }
    }

    @Test
    fun `should update user list state with users by pagination`() = runTest(testDispatcher) {
        val testQuery = "query"
        val testUsers = listOf<User>(mockk())
        val newUsers = listOf<User>(mockk(), mockk())
        val newPage = 2
        val lastIndexToUpdate = testPerPage - 1
        coEvery {
            userListRepository.getUserList(testQuery, testPerPage, testPage)
        } returns testUsers
        coEvery {
            userListRepository.getUserList(testQuery, testPerPage, newPage)
        } returns newUsers
        viewModel.trySearchingUsers(testQuery)
        advanceUntilIdle()
        viewModel.updateUsers(lastIndexToUpdate)
        advanceUntilIdle()
        val expectedResult = UserListResult.Success(testUsers.plus(newUsers))
        val actualResult = viewModel.userListState.value
        Assertions.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `should not update user list state with users if query is empty`() = runTest(testDispatcher) {
        val testQuery = ""
        val testUsers = listOf<User>(mockk())
        coEvery {
            userListRepository.getUserList(testQuery, testPerPage, testPage)
        } returns testUsers
        viewModel.trySearchingUsers(testQuery)
        advanceUntilIdle()
        coVerify(exactly = 0) {
            userListRepository.getUserList(testQuery, testPerPage, testPage)
        }
    }

    @Test
    fun `should not update user list state with users if query is blank`() = runTest(testDispatcher) {
        val testQuery = " "
        val testUsers = listOf<User>(mockk())
        coEvery {
            userListRepository.getUserList(testQuery, testPerPage, testPage)
        } returns testUsers
        viewModel.trySearchingUsers(testQuery)
        advanceUntilIdle()
        coVerify(exactly = 0) {
            userListRepository.getUserList(testQuery, testPerPage, testPage)
        }
    }

    @Test
    fun `should update user list state with error`() = runTest(testDispatcher) {
        val testQuery = "query"
        val testException = mockk<Exception>()
        coEvery {
            userListRepository.getUserList(testQuery, testPerPage, testPage)
        } throws testException
        val expectedResult = UserListResult.Error(testException)
        viewModel.trySearchingUsers(testQuery)
        advanceUntilIdle()
        val actualResult = viewModel.userListState.value
        Assertions.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `should update user list empty state`() = runTest(testDispatcher) {
        val testQuery = "query"
        coEvery {
            userListRepository.getUserList(testQuery, testPerPage, testPage)
        } returns emptyList()
        val expectedResult = UserListResult.Empty
        viewModel.trySearchingUsers(testQuery)
        advanceUntilIdle()
        val actualResult = viewModel.userListState.value
        Assertions.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `should update user list idle state`() = runTest(testDispatcher) {
        val expectedResult = UserListResult.Clear
        val actualResult = viewModel.userListState.value
        Assertions.assertEquals(expectedResult, actualResult)
    }
}