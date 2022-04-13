package com.example.core.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    fun launchWithCancellation(
        loadingBlock: suspend () -> Unit = {},
        block: suspend () -> Unit,
        errorBlock: suspend (Exception) -> Unit
    ) {
        viewModelScope.launch {
            loadingBlock.invoke()
            try {
                block.invoke()
            } catch (exception: Exception) {
                if (exception is CancellationException) {
                    throw exception
                }
                errorBlock.invoke(exception)
            }
        }
    }
}