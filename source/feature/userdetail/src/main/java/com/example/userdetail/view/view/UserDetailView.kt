package com.example.userdetail.view.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.userdetail.R
import com.example.userdetail.presentation.model.UserDetailResult

class UserDetailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var userDetailAvatar: ImageView? = null
    private var userDetailLogin: TextView? = null
    private var userDetailEmail: TextView? = null

    init {
        bind()
    }

    fun populate(userDetailResult: UserDetailResult) {

    }

    private fun bind() {
        userDetailAvatar = findViewById(R.id.userDetailAvatar)
        userDetailLogin = findViewById(R.id.userDetailLogin)
        userDetailEmail = findViewById(R.id.userDetailEmail)
    }
}