package com.example.gitusersearch.view.delegate

import android.content.Context
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.userdetail.view.model.UserPhotoToolbar
import com.example.userlist.view.model.SearchHintToolbar
import com.example.viewcore.ext.setImage
import com.example.viewcore.fragment.BaseFragment
import com.example.viewcore.model.Empty
import com.example.viewcore.model.ToolbarData
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.coroutines.flow.MutableStateFlow

class ActionBarDelegate(
    private val supportFragmentManager: FragmentManager,
    private val lifecycleScope: LifecycleCoroutineScope
) {

    private val toolbarDataState = MutableStateFlow<ToolbarData>(Empty)

    init {
        listenToFragmentAttached()
    }

    private fun listenToFragmentAttached() {
        supportFragmentManager.registerFragmentLifecycleCallbacks(
            object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
                    if (f is BaseFragment) {
                        updateToolbar(f)
                    }
                }

                override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {

                }
            },
            true
        )
    }

    fun observeToolbarDataState(
        actionBar: ActionBar,
        collapsingToolbarLayout: CollapsingToolbarLayout,
        imageView: ImageView, window: Window
    ) {
        lifecycleScope.launchWhenCreated {
            toolbarDataState.collect { toolbarData ->
                when (toolbarData) {
                    is SearchHintToolbar -> updateSearchHintToolbar(
                        collapsingToolbarLayout,
                        actionBar,
                        imageView,
                        toolbarData
                    )
                    is UserPhotoToolbar -> updatePhotoToolbar(collapsingToolbarLayout, window, imageView, toolbarData)
                }
            }
        }
    }

    private fun updateToolbar(baseFragment: BaseFragment) {
        lifecycleScope.launchWhenCreated {
            val data = baseFragment.toolbarData
            toolbarDataState.emit(data)
        }
    }

    private fun updateSearchHintToolbar(
        collapsingToolbarLayout: CollapsingToolbarLayout,
        actionBar: ActionBar,
        imageView: ImageView,
        toolbarData: SearchHintToolbar
    ) {
        collapsingToolbarLayout.isTitleEnabled = false
        actionBar.title = toolbarData.hint
        imageView.setImageDrawable(null)
    }

    private fun updatePhotoToolbar(
        collapsingToolbarLayout: CollapsingToolbarLayout,
        window: Window,
        imageView: ImageView,
        toolbarData: UserPhotoToolbar
    ) {
        collapsingToolbarLayout.isTitleEnabled = true
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
        collapsingToolbarLayout.title = toolbarData.username
        imageView.setImage(toolbarData.uri)
    }
}