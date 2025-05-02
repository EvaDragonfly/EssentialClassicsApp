package com.memoittech.cuviewtv.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memoittech.cuviewtv.ApiConstants
import com.memoittech.cuviewtv.TokenManager
import com.memoittech.cuviewtv.model.MoodTracksResponse
import com.memoittech.cuviewtv.model.MoodsResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MoodsViewModel : ViewModel() {

    var moodsResponse by mutableStateOf<MoodsResponse?>(null)
    var errorMessage : String by mutableStateOf("")

    var moodTracksResponse by mutableStateOf<MoodTracksResponse?>(null)

    fun getMoods() {
        viewModelScope.launch {
            ApiConstants.retrofit.getMoods("Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<MoodsResponse>{
                override fun onResponse(call: Call<MoodsResponse>, response: Response<MoodsResponse>) =
                    if(!response.isSuccessful){
                        errorMessage = response.message()
                    } else {
                        moodsResponse = response.body()
                    }

                override fun onFailure(call: Call<MoodsResponse>, response: Throwable) {
                    errorMessage = response.toString()
                }

            })
        }
    }

    fun getMoodTracks(mood_id : Int){
        viewModelScope.launch {
            ApiConstants.retrofit.getMoodTracks("Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<MoodTracksResponse>{
                override fun onResponse(
                    call: Call<MoodTracksResponse>,
                    response: Response<MoodTracksResponse>
                ) {
                    if(!response.isSuccessful){
                        errorMessage = response.message()
                    } else {
                        moodTracksResponse = response.body()
                    }
                }

                override fun onFailure(call: Call<MoodTracksResponse>, response: Throwable) {
                    errorMessage = response.toString()
                }

            })
        }
    }
}