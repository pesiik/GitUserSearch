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
        val testUrl = "rul"
        val testCompany = "company"
        val testLocation = "location"
        val testEmail = "email"
        val testBio = "bio"
        val testUserResponse = mockk<UserDetailResponse> {
            every {
                login
            } returns testLogin
            every {
                url
            } returns testUrl
            every {
                company
            } returns testCompany
            every {
                location
            } returns testLocation
            every {
                email
            } returns testEmail
            every {
                bio
            } returns testBio
        }
        val expectedUserDetail = UserDetail(testLogin, testCompany, testLocation, testUrl, testEmail, testBio)
        val actualUserDetail = mapper.mapUserDetailResponse(testUserResponse)
        Assertions.assertEquals(expectedUserDetail, actualUserDetail)
    }
}