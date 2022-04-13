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
        val rightDrawable = getDrawable(R.drawable.ic_copy)
        return UserDetailResult.Success(
            createCompanyBlock(userDetail, rightDrawable),
            createEmailBlock(userDetail, rightDrawable),
            createLocationBlock(userDetail, rightDrawable),
            createUrlBlock(userDetail, rightDrawable),
            createBio(userDetail)
        )
    }

    private fun createCompanyBlock(userDetail: UserDetail, rightDrawable: Drawable): UserDetailBlockModel {
        val companyDrawable = getDrawable(R.drawable.ic_company)
        val companyString = getStringOrFromResources(userDetail.company, R.string.user_detail_company_stub)
        return UserDetailBlockModel(
            companyDrawable,
            rightDrawable,
            companyString,
            resourcesWrapper.getString(R.string.user_detail_company_subtitle),
            userDetail.company.isNotEmpty()
        )
    }

    private fun createEmailBlock(userDetail: UserDetail, rightDrawable: Drawable): UserDetailBlockModel {
        val mailDrawable = getDrawable(R.drawable.ic_mail)
        val emailString = getStringOrFromResources(userDetail.email, R.string.user_detail_mail_stub)
        return UserDetailBlockModel(
            mailDrawable,
            rightDrawable,
            emailString,
            resourcesWrapper.getString(R.string.user_detail_mail_subtitle),
            userDetail.email.isNotEmpty(),
            true
        )
    }

    private fun createLocationBlock(userDetail: UserDetail, rightDrawable: Drawable): UserDetailBlockModel {
        val locationDrawable = getDrawable(R.drawable.ic_city)
        val locationString = getStringOrFromResources(userDetail.location, R.string.user_detail_location_stub)
        return UserDetailBlockModel(
            locationDrawable,
            rightDrawable,
            locationString,
            resourcesWrapper.getString(R.string.user_detail_location_subtitle),
            userDetail.location.isNotEmpty()
        )
    }

    private fun createUrlBlock(userDetail: UserDetail, rightDrawable: Drawable): UserDetailBlockModel {
        val urlDrawable = getDrawable(R.drawable.ic_link)
        val urlString = getStringOrFromResources(userDetail.url, R.string.user_detail_url_stub)
        return UserDetailBlockModel(
            urlDrawable,
            rightDrawable,
            urlString,
            resourcesWrapper.getString(R.string.user_detail_url_subtitle),
            userDetail.url.isNotEmpty(),
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