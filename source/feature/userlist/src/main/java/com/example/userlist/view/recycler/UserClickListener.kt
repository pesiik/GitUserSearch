package com.example.userlist.view.recycler

import com.example.userlist.domain.model.User

interface UserClickListener {
    fun onItemClick(user: User)
}