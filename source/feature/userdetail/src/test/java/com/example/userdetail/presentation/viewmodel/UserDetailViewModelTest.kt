package com.example.userdetail.presentation.viewmodel

import com.example.userdetail.data.repository.UserDetailRepository
import com.example.userdetail.domain.UserDetail
import com.example.userdetail.presentation.factory.UserDetailResultFactory
import com.example.userdetail.presentation.model.UserDetailResult
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
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
class UserDetailViewModelTest {

    private val testUsername = "user"
    private val userDetailRepository = mockk<UserDetailRepository>()
    private val userDetailResultFactory = mockk<UserDetailResultFactory>()
    private lateinit var viewModel: UserDetailViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        clearAllMocks()
        Dispatchers.setMain(testDispatcher)
        viewModel = UserDetailViewModel(testUsername, userDetailRepository, userDetailResultFactory)
    }

    @Test
    fun `should update user detail state`() = runTest(testDispatcher) {
        val testUser = UserDetail(
            "login",
            "company",
            "location",
            "url",
            "email",
            "bio"
        )
        val testResult = mockk<UserDetailResult.Success>()
        coEvery {
            userDetailRepository.getUserByName(testUsername)
        } returns testUser
        every {
            userDetailResultFactory.getUserDetailResult(testUser)
        } returns testResult
        advanceTimeBy(1000L)
        val actualResult = viewModel.userDetailState.value
        Assertions.assertEquals(testResult, actualResult)
    }

    @Test
    fun `should update user detail state with error`() = runTest(testDispatcher) {
        val testException = mockk<Exception>()
        coEvery {
            userDetailRepository.getUserByName(testUsername)
        } throws testException
        val expectedResult = UserDetailResult.Error(testException)
        advanceTimeBy(1001L)
        val actualResult = viewModel.userDetailState.value
        Assertions.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `should update empty user detail state`() = runTest(testDispatcher) {
        val testUser = UserDetail(
            "login",
            "company",
            ",location",
            "url",
            "email",
            "bio"
        )
        coEvery {
            userDetailRepository.getUserByName(testUsername)
        } returns testUser
        val expectedResult = UserDetailResult.Empty
        val actualResult = viewModel.userDetailState.value
        Assertions.assertEquals(expectedResult, actualResult)
    }
}