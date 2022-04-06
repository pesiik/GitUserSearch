package com.example.userlist.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.core.mvvm.ViewModelFactory
import com.example.userlist.R
import com.example.userlist.presentation.viewmodel.UserListViewModel
import com.example.userlist.view.ext.inject
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

class UserListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: UserListViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return LayoutInflater.from(requireContext()).inflate(R.layout.user_list_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.searchUsers("kek")
        lifecycleScope.launchWhenCreated {
            viewModel.userListState.collect { users ->
                if (users.isNotEmpty()) {
                    Log.d("Mashin", users[0].url)
                }
            }
        }
    }
}