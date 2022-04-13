package com.example.userdetail.view.model

import android.graphics.drawable.Drawable

data class UserDetailBlockModel(
    val leftDrawable: Drawable,
    val rightDrawable: Drawable,
    val mainInfo: String,
    val subInfo: String,
    val canCopy: Boolean,
    val showDivider: Boolean = false
)