package com.example.userlist.view.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.userlist.R
import com.example.userlist.domain.model.User
import com.example.userlist.presentation.model.UserListResult
import com.example.userlist.view.recycler.UserClickListener
import com.example.userlist.view.recycler.UserListAdapter


class UserListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), UserClickListener {

    var onItemClick: (String, String) -> Unit = { _, _ -> }
    var onError: () -> Unit = {}

    private var userRecyclerView: RecyclerView? = null
    private var adapter: UserListAdapter? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        bind()
    }

    fun populate(userListResult: UserListResult) {
        when (userListResult) {
            is UserListResult.Success -> adapter?.update(userListResult.users)
            is UserListResult.Empty -> Unit
            is UserListResult.Error -> onError.invoke()
        }
    }

    override fun onItemClick(user: User) {
        onItemClick.invoke(user.login, user.avatarURL)
    }

    private fun bind() {
        userRecyclerView = findViewById(R.id.userRecyclerView)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = UserListAdapter(this)
        userRecyclerView?.adapter = adapter
    }
}