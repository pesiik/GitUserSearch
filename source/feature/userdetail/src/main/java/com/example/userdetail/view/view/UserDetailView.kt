package com.example.userdetail.view.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.userdetail.R
import com.example.userdetail.presentation.model.UserDetailResult
import com.example.viewcore.ext.setImage

class UserDetailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var userDetailAvatar: ImageView? = null
    private var userDetailLogin: TextView? = null
    private var userDetailUrl: TextView? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        bind()
    }

    fun populate(userDetailResult: UserDetailResult) {
        when (userDetailResult) {
            is UserDetailResult.Success -> renderUserDetail(userDetailResult)
            is UserDetailResult.Empty -> Unit
            is UserDetailResult.Error -> {} //todo
        }
    }

    private fun bind() {
        userDetailAvatar = findViewById(R.id.userDetailAvatar)
        userDetailLogin = findViewById(R.id.userDetailLogin)
        userDetailUrl = findViewById(R.id.userDetailUrl)
    }

    private fun renderUserDetail(result: UserDetailResult.Success) {
        with(result.userDetail) {
            userDetailAvatar?.setImage(avatarUrl)
            userDetailLogin?.text = login
            userDetailUrl?.text = url
        }
    }
}