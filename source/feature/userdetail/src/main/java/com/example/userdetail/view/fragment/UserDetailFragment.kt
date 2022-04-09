package com.example.userdetail.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.core.mvvm.ViewModelFactory
import com.example.userdetail.R
import com.example.userdetail.presentation.viewmodel.UserDetailViewModel
import com.example.userdetail.view.ext.inject
import com.example.userdetail.view.view.UserDetailView
import javax.inject.Inject

class UserDetailFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: UserDetailViewModel by viewModels { viewModelFactory }

    private val args = navArgs<UserDetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(args.value.username)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.close()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return LayoutInflater.from(requireContext()).inflate(R.layout.user_detail_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind(view as UserDetailView)
    }

    private fun bind(userDetailView: UserDetailView) {
        lifecycleScope.launchWhenCreated {
            viewModel.userDetailState.collect(userDetailView::populate)
        }
    }
}