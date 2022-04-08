package com.example.userlist.view.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.userlist.R
import com.example.userlist.presentation.model.UserListResult
import com.example.userlist.view.recycler.UserListAdapter

class UserListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var onQueryAction: (String) -> Unit = {}

    private var userSearchView: SearchView? = null
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
            is UserListResult.Error -> Unit //todo
        }
    }

    private fun bind() {
        userSearchView = findViewById(R.id.userSearchView)
        userRecyclerView = findViewById(R.id.userRecyclerView)
        setupSearchView()
        setupRecyclerView()
    }

    private fun setupSearchView() {
        userSearchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let(onQueryAction::invoke)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let(onQueryAction::invoke)
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        userRecyclerView?.setHasFixedSize(true)
        adapter = UserListAdapter()
        userRecyclerView?.adapter = adapter
    }
}