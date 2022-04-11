package com.example.userdetail.domain

data class UserDetail(
    val login: String,
    val company: String,
    val location: String,
    val url: String,
    val email: String,
    val bio: String
) {

    val hasEmail: Boolean = email.isNotEmpty()
    val hasNumber = false
    val hasMessenger = false
    val hasBio = bio.isNotEmpty()
    val hasCompany = company.isNotEmpty()
    val hasLocation = location.isNotEmpty()
}