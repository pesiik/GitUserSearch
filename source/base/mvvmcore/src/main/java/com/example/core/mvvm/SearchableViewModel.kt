package com.example.core.mvvm

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class SearchableViewModel : BaseViewModel() {

    private var searchJob: Job? = null

    protected open fun trySearching(query: String, action: suspend () -> Unit) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(DEBOUNCE_PERIOD)
            if (query.isNotBlank() && query.isNotEmpty()) {
                action.invoke()
            }
        }
    }

    companion object {
        const val DEBOUNCE_PERIOD = 1000L
    }
}