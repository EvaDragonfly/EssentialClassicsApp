package com.memoittech.cuviewtv.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memoittech.cuviewtv.ApiConstants
import com.memoittech.cuviewtv.TokenManager
import com.memoittech.cuviewtv.model.MoodTracksResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MoodsViewModel : ViewModel() {

    var errorMessage : String by mutableStateOf("")

    var moodTracksResponse by mutableStateOf<MoodTracksResponse?>(null)

    fun getMoodTracks(moodId : Int){
        viewModelScope.launch {
            ApiConstants.retrofit.getMoodTracks("Token ${TokenManager.getToken()}", moodId).enqueue(object : retrofit2.Callback<MoodTracksResponse>{
                override fun onResponse(
                    call: Call<MoodTracksResponse>,
                    response: Response<MoodTracksResponse>
                ) {
                    if(!response.isSuccessful){
                        errorMessage = response.message()
                        Log.d("MY_TAG", errorMessage)
                    } else {
                        moodTracksResponse = response.body()
                        Log.d("MY_TAG", moodTracksResponse?.data.toString())
                        Log.d("MY_TAG", moodTracksResponse?.data?.size.toString())
                    }
                }

                override fun onFailure(call: Call<MoodTracksResponse>, response: Throwable) {
                    errorMessage = response.toString()
                    Log.d("MY_TAG", errorMessage)
                }

            })
        }
    }
}