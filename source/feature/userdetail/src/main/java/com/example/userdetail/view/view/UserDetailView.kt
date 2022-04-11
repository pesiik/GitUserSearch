package com.example.userdetail.view.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.userdetail.R
import com.example.userdetail.domain.UserDetail
import com.example.userdetail.presentation.model.UserDetailResult

class UserDetailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var userDetailLocation: TextView? = null
    private var userDetailCompany: TextView? = null
    private var userDetailUrl: TextView? = null
    private var userDetailBio: TextView? = null
    private var userDetailEmailButton: ImageButton? = null
    private var userDetailCallButton: ImageButton? = null
    private var userDetailMessageButton: ImageButton? = null


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
        userDetailLocation = findViewById(R.id.userDetailLocation)
        userDetailCompany = findViewById(R.id.userDetailCompany)
        userDetailUrl = findViewById(R.id.userDetailUrl)
        userDetailBio = findViewById(R.id.userDetailBio)
        userDetailEmailButton = findViewById(R.id.userDetailEmailButton)
        userDetailCallButton = findViewById(R.id.userDetailCallButton)
        userDetailMessageButton = findViewById(R.id.userDetailMessageButton)
    }

    private fun renderUserDetail(result: UserDetailResult.Success) {
        with(result.userDetail) {
            userDetailUrl?.text = url
            setupCompany(this)
            setupLocation(this)
            setupBio(this)
            setupEmailButton(this)
            setupCallButton(this)
            setupMessageButton(this)
        }
    }

    private fun setupCompany(user: UserDetail) {
        with(user) {
            if (hasCompany) {
                val companyTitle = resources.getString(R.string.user_detail_company, company)
                userDetailCompany?.text = companyTitle
            } else {
                userDetailCompany?.text = resources.getString(R.string.user_detail_company_stub)
            }
        }
    }

    private fun setupLocation(user: UserDetail) {
        with(user) {
            if (hasLocation) {
                userDetailLocation?.text = location
            } else {
                userDetailLocation?.text = resources.getString(R.string.user_detail_location_stub)
            }
        }
    }

    private fun setupBio(user: UserDetail) {
        if (user.hasBio) {
            userDetailBio?.text = user.bio
        } else {
            userDetailBio?.text = resources.getString(R.string.user_detail_bio_stub)
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun setupEmailButton(user: UserDetail) {
        with(user) {
            userDetailEmailButton?.isEnabled = hasEmail
            userDetailEmailButton?.setOnClickListener {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:")
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                }
            }
        }
    }

    private fun setupCallButton(user: UserDetail) {
        with(user) {
            userDetailCallButton?.isEnabled = hasNumber
        }
    }

    private fun setupMessageButton(user: UserDetail) {
        with(user) {
            userDetailMessageButton?.isEnabled = hasMessenger
        }
    }
}