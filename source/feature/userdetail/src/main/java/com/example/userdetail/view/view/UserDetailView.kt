package com.example.userdetail.view.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.userdetail.R
import com.example.userdetail.presentation.model.UserDetailResult

class UserDetailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var onError: () -> Unit = {}

    private var userDetailCompany: UserDetailBlock? = null
    private var userDetailEmail: UserDetailBlock? = null
    private var userDetailLocation: UserDetailBlock? = null
    private var userDetailUrl: UserDetailBlock? = null
    private var userDetailBio: TextView? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        bind()
    }

    fun populate(userDetailResult: UserDetailResult) {
        when (userDetailResult) {
            is UserDetailResult.Success -> renderUserDetail(userDetailResult)
            is UserDetailResult.Empty -> Unit
            is UserDetailResult.Error -> onError.invoke()
        }
    }

    private fun bind() {
        userDetailCompany = findViewById(R.id.companyBlock)
        userDetailEmail = findViewById(R.id.emailBlock)
        userDetailLocation = findViewById(R.id.locationBlock)
        userDetailUrl = findViewById(R.id.urlBlock)
        userDetailBio = findViewById(R.id.userDetailBio)
    }

    private fun renderUserDetail(result: UserDetailResult.Success) {
        with(result) {
            userDetailCompany?.populate(companyModel)
            userDetailEmail?.populate(emailModel)
            userDetailLocation?.populate(locationModel)
            userDetailUrl?.populate(urlModel)
            userDetailBio?.text = bio
        }
    }
}