package com.example.userlist.view.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
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
    var onRecyclerScroll: (Int) -> Unit = {}

    private var userRecyclerView: RecyclerView? = null
    private var userEmptyView: LinearLayoutCompat? = null
    private var adapter: UserListAdapter? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        bind()
    }

    fun populate(userListResult: UserListResult) {
        when (userListResult) {
            is UserListResult.Clear -> {
                adapter?.update(emptyList())
            }
            is UserListResult.Success -> {
                showRecycler(true)
                adapter?.update(userListResult.users)
            }
            is UserListResult.Empty -> {
                showRecycler(false)
            }
            is UserListResult.Error -> onError.invoke()
            is UserListResult.Idle -> Unit
        }
    }

    override fun onItemClick(user: User) {
        onItemClick.invoke(user.login, user.avatarURL)
    }

    private fun bind() {
        userRecyclerView = findViewById(R.id.userRecyclerView)
        userEmptyView = findViewById(R.id.userEmptyView)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = UserListAdapter(this)
        userRecyclerView?.adapter = adapter
        val layoutManager = userRecyclerView?.layoutManager as GridLayoutManager
        userRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                onRecyclerScroll.invoke(lastVisiblePosition)
            }
        })
    }

    private fun showRecycler(show: Boolean) {
        userRecyclerView?.isVisible = show
        userEmptyView?.isVisible = !show
    }
}