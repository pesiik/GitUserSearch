package com.example.userlist.view.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.userlist.R
import com.example.userlist.domain.model.User
import com.example.viewcore.ext.setImage

class UserListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun populate(user: User, userClickListener: UserClickListener) {
        val image = view.findViewById<ImageView>(R.id.userAvatar)
        val imageStub = ResourcesCompat.getDrawable(view.resources, R.drawable.image_stub, view.context.theme)
        image.setImage(user.avatarURL, imageStub)
        val userNameView = view.findViewById<TextView>(R.id.userNameView)
        userNameView.text = user.login
        val userLinkView = view.findViewById<TextView>(R.id.userLink)
        userLinkView.text = user.url
        itemView.setOnClickListener {
            userClickListener.onItemClick(user)
        }
    }
}