package com.example.userlist.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("total_count")
    val totalCount: Long,

    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    val items: List<UserData>
)