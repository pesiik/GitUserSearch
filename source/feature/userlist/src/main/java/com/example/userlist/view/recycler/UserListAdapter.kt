package com.example.userlist.view.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.userlist.R
import com.example.userlist.domain.model.User

class UserListAdapter : ListAdapter<User, UserListViewHolder>(UserListDiffUtilCallback()) {

    private val users: MutableList<User> = mutableListOf()

    fun update(newUsers: List<User>) {
        users.clear()
        users.addAll(newUsers)
        submitList(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return UserListViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        onBindViewHolder(holder, position, mutableListOf())
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int, payloads: MutableList<Any>) {
        val user = users[position]
        if (payloads.isEmpty()) {
            holder.populate(user)
        }
    }
}