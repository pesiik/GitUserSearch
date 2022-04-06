package com.example.userlist.domain.model

data class User(
    val login: String,
    val id: Long,
    val avatarURL: String,
    val url: String,
    val score: Long
)