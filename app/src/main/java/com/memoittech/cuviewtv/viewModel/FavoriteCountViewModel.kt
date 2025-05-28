package com.memoittech.cuviewtv.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memoittech.cuviewtv.ApiConstants
import com.memoittech.cuviewtv.TokenManager
import com.memoittech.cuviewtv.model.FavoriteCountData
import com.memoittech.cuviewtv.model.FavoritesCountResponse
import com.memoittech.cuviewtv.model.MoodTracksResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class FavoriteCountViewModel : ViewModel() {

    var errorMessage : String by mutableStateOf("")

    var favoriteCountResponse by mutableStateOf<FavoritesCountResponse?>(null)

    fun getFavoriteCount(){
        viewModelScope.launch {
            ApiConstants.retrofit.getFavoriteCount("Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<FavoritesCountResponse>{
                override fun onResponse(
                    call: Call<FavoritesCountResponse>,
                    response: Response<FavoritesCountResponse>
                ) {
                    if(!response.isSuccessful){
                        errorMessage = response.message()
                    } else {
                        favoriteCountResponse = response.body()
                    }
                }

                override fun onFailure(call: Call<FavoritesCountResponse>, response: Throwable) {
                    errorMessage = response.toString()
                }

            })
        }
    }

}