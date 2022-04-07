package com.example.userlist.data.mapper

import com.example.userlist.data.model.UserData
import com.example.userlist.data.model.UserResponse
import com.example.userlist.domain.model.User
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UserListMapperTest {

    private val mapper = UserListMapper()

    @Test
    fun `should map response to user list`() {
        val firstTestUserData = createUserData(1)
        val secondTestUserData = createUserData(2)
        val testUserResponse = UserResponse(
            0,
            false,
            listOf(firstTestUserData, secondTestUserData)
        )
        val expectedUsers = listOf(
            User("login1", 1, "avatarUrl1", "url1", 1),
            User("login2", 2, "avatarUrl2", "url2", 2)
        )
        val actualUsers = mapper.mapUserListResponse(testUserResponse)
        Assertions.assertEquals(expectedUsers, actualUsers)
    }

    private fun createUserData(userId: Long): UserData {
        val testLogin = "login$userId"
        val testAvatarUrl = "avatarUrl$userId"
        val testUrl = "url$userId"
        return mockk(relaxed = true) {
            every {
                login
            } returns testLogin
            every {
                id
            } returns userId
            every {
                avatarURL
            } returns testAvatarUrl
            every {
                url
            } returns testUrl
            every {
                score
            } returns userId
        }
    }
}