package com.example.userdetail.data.mapper

import com.example.userdetail.data.model.UserDetailResponse
import com.example.userdetail.domain.UserDetail
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UserDetailMapperTest {

    private val mapper = UserDetailMapper()

    @Test
    fun `should map user detail response`() {
        val testLogin = "login"
        val testAvatarURL = "avatarURL"
        val testEmail = "email"
        val testUserResponse = mockk<UserDetailResponse> {
            every {
                login
            } returns testLogin
            every {
                avatarURL
            } returns testAvatarURL
            every {
                email
            } returns testEmail
        }
        val expectedUserDetail = UserDetail(testLogin, testAvatarURL, testEmail)
        val actualUserDetail = mapper.mapUserDetailResponse(testUserResponse)
        Assertions.assertEquals(expectedUserDetail, actualUserDetail)
    }
}