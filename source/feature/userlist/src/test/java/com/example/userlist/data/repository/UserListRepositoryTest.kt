package com.example.userlist.data.repository

import com.example.userlist.data.UserListApi
import com.example.userlist.data.mapper.UserListMapper
import com.example.userlist.data.model.UserData
import com.example.userlist.data.model.UserResponse
import com.example.userlist.domain.model.User
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserListRepositoryTest {

    private val userListApi = mockk<UserListApi>()
    private val userListMapper = mockk<UserListMapper>()
    private lateinit var repository: UserListRepository

    @BeforeEach
    fun setUp() {
        repository = UserListRepository(userListApi, userListMapper)
    }

    @Test
    fun `should get users by query`() = runBlocking {
        val testQuery = "query"
        val testUserData = mockk<UserData>()
        val testUsersData = listOf(testUserData)
        val testUserResponse = UserResponse(0, false, testUsersData)
        val expectedUsers = listOf(mockk<User>())
        coEvery {
            userListApi.getUserList(testQuery)
        } returns testUserResponse
        every {
            userListMapper.mapUserListResponse(testUserResponse)
        } returns expectedUsers
        val users = repository.getUserList(testQuery)
        Assertions.assertEquals(expectedUsers, users)
    }
}