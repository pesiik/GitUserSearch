package com.example.userlist.view.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.userlist.domain.model.User

class UserListDiffUtilCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.login == newItem.login &&
            oldItem.avatarURL == newItem.avatarURL &&
            oldItem.score == newItem.score &&
            oldItem.url == newItem.url
    }
}