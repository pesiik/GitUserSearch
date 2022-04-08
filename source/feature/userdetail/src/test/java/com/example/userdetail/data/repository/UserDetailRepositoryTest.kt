package com.example.userdetail.data.repository

import com.example.userdetail.data.UserDetailApi
import com.example.userdetail.data.mapper.UserDetailMapper
import com.example.userdetail.data.model.UserDetailResponse
import com.example.userdetail.domain.UserDetail
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserDetailRepositoryTest {

    private val testUsername = "username"
    private val userDetailApi = mockk<UserDetailApi>()
    private val userDetailMapper = mockk<UserDetailMapper>()
    private lateinit var repository: UserDetailRepository

    @BeforeEach
    fun setUp() {
        clearAllMocks()
        repository = UserDetailRepository(userDetailApi, userDetailMapper)
    }

    @Test
    fun `should get user detail`() = runBlocking {
        val testUserDetailResponse = mockk<UserDetailResponse>()
        val testUserDetail = mockk<UserDetail>()
        coEvery {
            userDetailApi.getUser(testUsername)
        } returns testUserDetailResponse
        every {
            userDetailMapper.mapUserDetailResponse(testUserDetailResponse)
        } returns testUserDetail
        Assertions.assertEquals(testUserDetail, repository.getUserByName(testUsername))
    }
}