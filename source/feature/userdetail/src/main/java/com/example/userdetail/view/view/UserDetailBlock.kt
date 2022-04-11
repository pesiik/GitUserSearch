package com.example.userdetail.view.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.userdetail.R
import com.example.userdetail.view.model.UserDetailBlockModel


class UserDetailBlock @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.user_detail_block, this)
    }

    fun populate(model: UserDetailBlockModel) {
        with(model) {
            val leftImageView = findViewById<ImageView>(R.id.leftImage)
            val mainInfoView = findViewById<TextView>(R.id.mainInfo)
            val subInfoView = findViewById<TextView>(R.id.subInfo)
            val divider = findViewById<View>(R.id.divider)
            leftImageView?.setImageDrawable(drawable)
            mainInfoView?.text = mainInfo
            subInfoView?.text = subInfo
            divider?.isVisible = showDivider
        }
    }
}