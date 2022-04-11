package com.example.userdetail.presentation.factory

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.userdetail.R
import com.example.userdetail.domain.UserDetail
import com.example.userdetail.presentation.model.UserDetailResult
import com.example.userdetail.view.model.UserDetailBlockModel
import com.example.viewcore.resources.ResourcesWrapper
import javax.inject.Inject

class UserDetailResultFactory @Inject constructor(
    private val resourcesWrapper: ResourcesWrapper
) {

    fun getUserDetailResult(userDetail: UserDetail): UserDetailResult.Success {
        return UserDetailResult.Success(
            createCompanyBlock(userDetail),
            createEmailBlock(userDetail),
            createLocationBlock(userDetail),
            createUrlBlock(userDetail),
            createBio(userDetail)
        )
    }

    private fun createCompanyBlock(userDetail: UserDetail): UserDetailBlockModel {
        val companyDrawable = getDrawable(R.drawable.ic_company)
        val companyString = getStringOrFromResources(userDetail.company, R.string.user_detail_company_stub)
        return UserDetailBlockModel(
            companyDrawable,
            companyString,
            resourcesWrapper.getString(R.string.user_detail_company_subtitle)
        )
    }

    private fun createEmailBlock(userDetail: UserDetail): UserDetailBlockModel {
        val mailDrawable = getDrawable(R.drawable.ic_mail)
        val emailString = getStringOrFromResources(userDetail.email, R.string.user_detail_mail_stub)
        return UserDetailBlockModel(
            mailDrawable,
            emailString,
            resourcesWrapper.getString(R.string.user_detail_mail_subtitle),
            true
        )
    }

    private fun createLocationBlock(userDetail: UserDetail): UserDetailBlockModel {
        val locationDrawable = getDrawable(R.drawable.ic_city)
        val locationString = getStringOrFromResources(userDetail.location, R.string.user_detail_location_stub)
        return UserDetailBlockModel(
            locationDrawable,
            locationString,
            resourcesWrapper.getString(R.string.user_detail_location_subtitle)
        )
    }

    private fun createUrlBlock(userDetail: UserDetail): UserDetailBlockModel {
        val urlDrawable = getDrawable(R.drawable.ic_link)
        val urlString = getStringOrFromResources(userDetail.url, R.string.user_detail_url_stub)
        return UserDetailBlockModel(
            urlDrawable,
            urlString,
            resourcesWrapper.getString(R.string.user_detail_url_subtitle),
            true
        )
    }

    private fun createBio(userDetail: UserDetail): String {
        return getStringOrFromResources(userDetail.bio, R.string.user_detail_bio_stub)
    }

    private fun getDrawable(@DrawableRes res: Int): Drawable {
        return requireNotNull(resourcesWrapper.getDrawable(res))
    }

    private fun getStringOrFromResources(mainString: String, @StringRes res: Int): String {
        return mainString.ifEmpty {
            resourcesWrapper.getString(res)
        }
    }
}