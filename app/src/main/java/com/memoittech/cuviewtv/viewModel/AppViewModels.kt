package com.memoittech.cuviewtv.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AppViewModels : ViewModel() {
    var index by mutableStateOf(0)
        private set

    fun onIndexChanged(newIndex: Int) {
        index = newIndex
    }

}