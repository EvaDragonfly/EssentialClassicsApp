package com.memoittech.cuviewtv.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.memoittech.cuviewtv.model.Mood

class AppViewModels : ViewModel() {
    var index by mutableStateOf(0)
        private set

    var query by mutableStateOf("")
        private set

    var moodIndex by mutableStateOf(9)
        private set

    fun onIndexChanged(newIndex: Int) {
        index = newIndex
    }

    fun onQueryChanged(newQuery: String) {
        query = newQuery
    }

    fun onMoodChanged(newMood : Int){
        moodIndex = newMood
    }
}