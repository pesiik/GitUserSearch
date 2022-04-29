package com.example.viewcore.view

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

interface ScopedView : CoroutineScope, View.OnAttachStateChangeListener {

    override val coroutineContext
        get() = SupervisorJob() + Dispatchers.Main

    override fun onViewAttachedToWindow(v: View?) {}

    override fun onViewDetachedFromWindow(v: View?) {
        coroutineContext.cancel()
    }
}