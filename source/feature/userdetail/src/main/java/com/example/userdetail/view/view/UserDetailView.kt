package com.example.userdetail.view.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.userdetail.R
import com.example.userdetail.presentation.model.UserDetailResult

class UserDetailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var onError: () -> Unit = {}
    var onRefresh: () -> Unit = {}

    private var userDetailRefresh: SwipeRefreshLayout? = null
    private var userDetailCompany: UserDetailBlock? = null
    private var userDetailEmail: UserDetailBlock? = null
    private var userDetailLocation: UserDetailBlock? = null
    private var userDetailUrl: UserDetailBlock? = null
    private var userDetailBio: TextView? = null
    private var userDetailBioImage: ImageView? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        bind()
        setupRefreshLayout()
    }

    fun populate(userDetailResult: UserDetailResult) {
        when (userDetailResult) {
            is UserDetailResult.Success -> renderUserDetail(userDetailResult)
            is UserDetailResult.Empty -> Unit
            is UserDetailResult.Error -> {
                onError.invoke()
                stopRefreshing()
                setVisibility(false)
            }
        }
    }

    private fun bind() {
        userDetailRefresh = findViewById(R.id.userDetailRefresh)
        userDetailCompany = findViewById(R.id.companyBlock)
        userDetailEmail = findViewById(R.id.emailBlock)
        userDetailLocation = findViewById(R.id.locationBlock)
        userDetailUrl = findViewById(R.id.urlBlock)
        userDetailBio = findViewById(R.id.userDetailBio)
        userDetailBioImage = findViewById(R.id.userDetailBioImage)
    }

    private fun renderUserDetail(result: UserDetailResult.Success) {
        stopRefreshing()
        setVisibility(true)
        with(result) {
            userDetailCompany?.populate(companyModel)
            userDetailEmail?.populate(emailModel)
            userDetailLocation?.populate(locationModel)
            userDetailUrl?.populate(urlModel)
            userDetailBio?.text = bio
        }
    }

    private fun setupRefreshLayout() {
        userDetailRefresh?.setOnRefreshListener {
            onRefresh.invoke()
        }
    }

    private fun stopRefreshing() {
        userDetailRefresh?.isRefreshing = false
    }

    private fun setVisibility(isVisible: Boolean) {
        userDetailCompany?.isVisible = isVisible
        userDetailEmail?.isVisible = isVisible
        userDetailLocation?.isVisible = isVisible
        userDetailUrl?.isVisible = isVisible
        userDetailBio?.isVisible = isVisible
        userDetailBioImage?.isVisible = isVisible
    }
}