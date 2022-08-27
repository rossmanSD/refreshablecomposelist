package net.rossmanges.refreshablecomposelist.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val mIsRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean>
        get() = mIsRefreshing.asStateFlow()
}