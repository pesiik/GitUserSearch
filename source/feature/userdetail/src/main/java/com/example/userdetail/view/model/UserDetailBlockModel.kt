package com.example.userdetail.view.model

import android.graphics.drawable.Drawable

data class UserDetailBlockModel(
    val drawable: Drawable,
    val mainInfo: String,
    val subInfo: String,
    val showDivider: Boolean = false
)