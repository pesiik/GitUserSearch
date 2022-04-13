package com.example.userlist.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.core.mvvm.ViewModelFactory
import com.example.userlist.R
import com.example.userlist.presentation.viewmodel.UserListViewModel
import com.example.userlist.view.ext.inject
import com.example.userlist.view.model.SearchHintToolbar
import com.example.userlist.view.view.UserListView
import com.example.viewcore.fragment.BaseFragment
import javax.inject.Inject

class UserListFragment : BaseFragment() {

    override val toolbarData: SearchHintToolbar
        get() = SearchHintToolbar(getString(R.string.search_hint))

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: UserListViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return LayoutInflater.from(requireContext()).inflate(R.layout.user_list_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind(view as UserListView)
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
        observeSearchMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        setupSearchView(menu)
    }

    private fun setupSearchView(menu: Menu) {
        val userSearchView = menu.findItem(R.id.search).actionView as SearchView
        userSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let(viewModel::trySearchingUsers)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let(viewModel::trySearchingUsers)
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onErrorDialogPositiveClick() {
        viewModel.searchAgain()
    }

    private fun bind(userListView: UserListView) {
        lifecycleScope.launchWhenStarted {
            viewModel.userListState.collect(userListView::populate)
        }
        userListView.onItemClick = { username, avatarUrl ->
            findNavController().navigate(UserListFragmentDirections.toUserDetail(username, avatarUrl))
        }
        userListView.onError = {
            showErrorDialog()
        }
    }

    private fun observeSearchMenu(menu: Menu) {
        lifecycleScope.launchWhenCreated {
            viewModel.queryState.collect { query ->
                val itemMenu = menu.findItem(R.id.search)
                val userSearchView = itemMenu.actionView as SearchView
                if (userSearchView.query != query) {
                    if (query.isNotEmpty()) {
                        itemMenu.expandActionView()
                    }
                    userSearchView.setQuery(query, false)
                }
            }
        }
    }
}