package com.example.userdetail.domain

data class UserDetail(
    val login: String,
    val company: String,
    val location: String,
    val url: String,
    val email: String,
    val bio: String
)