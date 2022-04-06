package com.example.userlist.data.model

import com.google.gson.annotations.SerializedName

data class UserData(
    val login: String,

    val id: Long,

    @SerializedName("node_id")
    val nodeID: String,

    @SerializedName("avatar_url")
    val avatarURL: String,

    @SerializedName("gravatar_id")
    val gravatarID: String,

    val url: String,

    @SerializedName("html_url")
    val htmlURL: String,

    @SerializedName("followers_url")
    val followersURL: String,

    @SerializedName("subscriptions_url")
    val subscriptionsURL: String,

    @SerializedName("organizations_url")
    val organizationsURL: String,

    @SerializedName("repos_url")
    val reposURL: String,

    @SerializedName("received_events_url")
    val receivedEventsURL: String,

    val type: String,

    val score: Long,

    @SerializedName("following_url")
    val followingURL: String,

    @SerializedName("gists_url")
    val gistsURL: String,

    @SerializedName("starred_url")
    val starredURL: String,

    @SerializedName("events_url")
    val eventsURL: String,

    @SerializedName("site_admin")
    val siteAdmin: Boolean
)