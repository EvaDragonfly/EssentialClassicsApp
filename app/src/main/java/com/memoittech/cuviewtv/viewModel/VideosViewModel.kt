package com.memoittech.cuviewtv.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memoittech.cuviewtv.ApiConstants
import com.memoittech.cuviewtv.TokenManager
import com.memoittech.cuviewtv.model.FavoriteVideosData
import com.memoittech.cuviewtv.model.FavoriteVideosResponse
import com.memoittech.cuviewtv.model.VideoDetailsData
import com.memoittech.cuviewtv.model.VideoDetailsResponse
import com.memoittech.cuviewtv.model.VideoResponse
import com.memoittech.cuviewtv.model.VideoTrackData
import com.memoittech.cuviewtv.model.VideoTracksResponse
import com.memoittech.cuviewtv.model.VideosData
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class VideosViewModel : ViewModel(){
    var videosResponse by mutableStateOf<VideosData?>(null)
    var sliderVideosResponse by mutableStateOf<VideosData?>(null)
    var videodetailResponse by mutableStateOf<VideoDetailsData?>(null)
    var favouriteVideoResponse by mutableStateOf<ResponseBody?>(null)
    var favouriteVideosResponse by mutableStateOf<FavoriteVideosData?>(null)
    var videoTracksResponse by mutableStateOf<VideoTrackData?>(null)
    var errorMessage : String by mutableStateOf("")

    fun getVideosList(limit : Int, offset : Int, ordering : String, q : String ) {
        viewModelScope.launch {
            ApiConstants.retrofit.getVideos(limit, offset, ordering, q, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<VideoResponse>{
                override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) =
                    if(!response.isSuccessful){
                        errorMessage = response.message()
                    } else {
                        videosResponse = response.body()?.data
                    }

                override fun onFailure(call: Call<VideoResponse>, response: Throwable) {
                    errorMessage = response.toString()
                }

            })
        }
    }


    fun getSliderVideosList(limit : Int, offset : Int, ordering : String, q : String ) {
        viewModelScope.launch {
            ApiConstants.retrofit.getVideos(limit, offset, ordering, q, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<VideoResponse>{
                override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) =
                    if(!response.isSuccessful){
                        errorMessage = response.message()
                    } else {
                        sliderVideosResponse = response.body()?.data
                    }

                override fun onFailure(call: Call<VideoResponse>, response: Throwable) {
                    errorMessage = response.toString()
                }

            })
        }
    }


    fun getVideoDetails(id : Int) {
        viewModelScope.launch {
            ApiConstants.retrofit.getVideo(id, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<VideoDetailsResponse>{
                override fun onResponse(call: Call<VideoDetailsResponse>, response: Response<VideoDetailsResponse>) =
                    if(!response.isSuccessful){
                        errorMessage = response.message()
                    } else {
                        videodetailResponse = response.body()?.data
                    }

                override fun onFailure(call: Call<VideoDetailsResponse>, response: Throwable) {
                    errorMessage = response.toString()
                }

            })
        }
    }


    fun addFavoriteVideo(id : Int, delete : Boolean ) {
        viewModelScope.launch {
            ApiConstants.retrofit.addFavoriteVideo(id, delete, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) =
                    if(!response.isSuccessful){
                        errorMessage = response.message()
                    } else {
                        favouriteVideoResponse  = response.body()
                    }

                override fun onFailure(call: Call<ResponseBody>, response: Throwable) {
                    errorMessage = response.toString()
                }

            })
        }
    }


    fun getFavoriteVideos(limit : Int, offset : Int, ordering : String) {
        viewModelScope.launch {
            ApiConstants.retrofit.getFavoriteVideos("Token ${TokenManager.getToken()}", limit, offset, ordering).enqueue(object : retrofit2.Callback<FavoriteVideosResponse>{
                override fun onResponse(call: Call<FavoriteVideosResponse>, response: Response<FavoriteVideosResponse>) =
                    if(!response.isSuccessful){
                        errorMessage = response.message()
                    } else {
                        favouriteVideosResponse  = response.body()?.data
                    }

                override fun onFailure(call: Call<FavoriteVideosResponse>, response: Throwable) {
                    errorMessage = response.toString()
                }

            })
        }
    }
    
    
    fun getVideoTracks(id : Int){
        viewModelScope.launch {
            ApiConstants.retrofit.getVideoTracks(id,"Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<VideoTracksResponse>{
                override fun onResponse(call: Call<VideoTracksResponse>, response: Response<VideoTracksResponse>) =
                    if(!response.isSuccessful){
                        errorMessage = response.message()
                    } else {
                        videoTracksResponse  = response.body()?.data
                    }

                override fun onFailure(call: Call<VideoTracksResponse>, response: Throwable) {
                    errorMessage = response.toString()
                }

            })
        }
    }
    
}

