package com.example.userdetail.presentation.factory

import android.graphics.drawable.Drawable
import com.example.userdetail.R
import com.example.userdetail.domain.UserDetail
import com.example.userdetail.presentation.model.UserDetailResult
import com.example.userdetail.view.model.UserDetailBlockModel
import com.example.viewcore.resources.ResourcesWrapper
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserDetailResultFactoryTest {

    private val resourcesWrapper = mockk<ResourcesWrapper>()
    private lateinit var factory: UserDetailResultFactory

    private val companyDrawable = mockk<Drawable>()
    private val companyStubText = "companyStub"
    private val companySubtitle = "companySubtitle"
    private val emailDrawable = mockk<Drawable>()
    private val emailStubText = "emailStub"
    private val emailSubtitle = "emailSubtitle"
    private val locationDrawable = mockk<Drawable>()
    private val locationStubText = "locationStub"
    private val locationSubtitle = "locationSubtitle"
    private val urlDrawable = mockk<Drawable>()
    private val urlStubText = "urlStub"
    private val urlSubtitle = "urlSubtitle"
    private val bioStubText = "bioStub"

    @BeforeEach
    fun setUp() {
        every {
            resourcesWrapper.getDrawable(R.drawable.ic_company)
        } returns companyDrawable
        every {
            resourcesWrapper.getString(R.string.user_detail_company_subtitle)
        } returns companySubtitle
        every {
            resourcesWrapper.getDrawable(R.drawable.ic_mail)
        } returns emailDrawable
        every {
            resourcesWrapper.getString(R.string.user_detail_mail_subtitle)
        } returns emailSubtitle
        every {
            resourcesWrapper.getDrawable(R.drawable.ic_city)
        } returns locationDrawable
        every {
            resourcesWrapper.getString(R.string.user_detail_location_subtitle)
        } returns locationSubtitle
        every {
            resourcesWrapper.getDrawable(R.drawable.ic_link)
        } returns urlDrawable
        every {
            resourcesWrapper.getString(R.string.user_detail_url_subtitle)
        } returns urlSubtitle
        factory = UserDetailResultFactory(resourcesWrapper)
    }

    @Test
    fun `should get user detail result`() {
        val testLogin = "login"
        val testCompany = "company"
        val testLocation = "location"
        val testUrl = "url"
        val testEmail = "email"
        val testBio = "bio"
        val userDetail = UserDetail(testLogin, testCompany, testLocation, testUrl, testEmail, testBio)
        val expectedResult = UserDetailResult.Success(
            createUserDetailBlockModel(companyDrawable, testCompany, companySubtitle, false),
            createUserDetailBlockModel(emailDrawable, testEmail, emailSubtitle, true),
            createUserDetailBlockModel(locationDrawable, testLocation, locationSubtitle, false),
            createUserDetailBlockModel(urlDrawable, testUrl, urlSubtitle, true),
            testBio
        )
        val actualResult = factory.getUserDetailResult(userDetail)
        Assertions.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `should get user detail result with stubs`() {
        every {
            resourcesWrapper.getString(R.string.user_detail_company_stub)
        } returns companyStubText
        every {
            resourcesWrapper.getString(R.string.user_detail_mail_stub)
        } returns emailStubText
        every {
            resourcesWrapper.getString(R.string.user_detail_location_stub)
        } returns locationStubText
        every {
            resourcesWrapper.getString(R.string.user_detail_url_stub)
        } returns urlStubText
        every {
            resourcesWrapper.getString(R.string.user_detail_bio_stub)
        } returns bioStubText
        val emptyString = ""
        val userDetail = UserDetail(emptyString, emptyString, emptyString, emptyString, emptyString, emptyString)
        val expectedResult = UserDetailResult.Success(
            createUserDetailBlockModel(companyDrawable, companyStubText, companySubtitle, false),
            createUserDetailBlockModel(emailDrawable, emailStubText, emailSubtitle, true),
            createUserDetailBlockModel(locationDrawable, locationStubText, locationSubtitle, false),
            createUserDetailBlockModel(urlDrawable, urlStubText, urlSubtitle, true),
            bioStubText
        )
        val actualResult = factory.getUserDetailResult(userDetail)
        Assertions.assertEquals(expectedResult, actualResult)
    }

    private fun createUserDetailBlockModel(
        drawable: Drawable,
        mainInfo: String,
        subInfo: String,
        showDivider: Boolean
    ): UserDetailBlockModel {
        return UserDetailBlockModel(drawable, mainInfo, subInfo, showDivider)
    }
}