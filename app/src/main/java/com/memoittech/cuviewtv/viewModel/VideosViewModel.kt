package com.memoittech.cuviewtv.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memoittech.cuviewtv.ApiConstants
import com.memoittech.cuviewtv.TokenManager
import com.memoittech.cuviewtv.model.FavoriteVideo
import com.memoittech.cuviewtv.model.FavoriteVideosData
import com.memoittech.cuviewtv.model.FavoriteVideosResponse
import com.memoittech.cuviewtv.model.MembersData
import com.memoittech.cuviewtv.model.Track
import com.memoittech.cuviewtv.model.Video
import com.memoittech.cuviewtv.model.VideoDetailsData
import com.memoittech.cuviewtv.model.VideoDetailsResponse
import com.memoittech.cuviewtv.model.VideoResponse
import com.memoittech.cuviewtv.model.VideosData
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class VideosViewModel : ViewModel(){
    var videos by mutableStateOf<VideosData?>(null)
    var searchVideos = mutableStateListOf<Video>()
    var favouriteVideos = mutableStateListOf<FavoriteVideo>()

    var isLoading by mutableStateOf(false)
        private set
    private var currentOffsetVideos = 0
    private val pageSizeVideos = 30

    var isSearchVideosLoading by mutableStateOf(false)
        private set

    var isFavoriteVideosLoading by mutableStateOf(false)
        private set
    private var currentOffsetFavVideos = 0
    private val pageSizeFavVideos = 30

    var sliderVideosResponse by mutableStateOf<VideosData?>(null)
    var videodetailResponse by mutableStateOf<VideoDetailsData?>(null)
    var favouriteVideoResponse by mutableStateOf<ResponseBody?>(null)
    var errorMessage : String by mutableStateOf("")

    fun getVideosList( ordering : String, q : String, index : Int  ) {

        if (isLoading) return
        isLoading = true


        viewModelScope.launch {
            ApiConstants.retrofit.getVideos(10, 0, ordering, q, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<VideoResponse>{
                override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
                    if (!response.isSuccessful) {
                        errorMessage = response.message()
                    } else {
                        videos = response.body()?.data
                    }
                    isLoading = false
                }

                override fun onFailure(call: Call<VideoResponse>, response: Throwable) {
                    isLoading = false
                    errorMessage = response.toString()
                }

            })
        }
    }


    fun getSearchVideosList( ordering : String, q : String, index : Int  ) {

        if (isSearchVideosLoading) return
        isSearchVideosLoading = true

        if(index == 0){
            currentOffsetVideos = 0
            searchVideos.clear()
        }

        viewModelScope.launch {
            ApiConstants.retrofit.getVideos(pageSizeVideos, currentOffsetVideos, ordering, q, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<VideoResponse>{
                override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
                    if (!response.isSuccessful) {
                        errorMessage = response.message()
                    } else {
                        val newVideos = response.body()?.data?.results ?: emptyList()
                        searchVideos.addAll(newVideos)
                        currentOffsetVideos += newVideos.size
                    }
                    isSearchVideosLoading = false
                }

                override fun onFailure(call: Call<VideoResponse>, response: Throwable) {
                    isSearchVideosLoading = false
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


    fun getVideoDetails(id: Int) {
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


    fun getFavoriteVideos( ordering : String) {

        if (isFavoriteVideosLoading) return
        isFavoriteVideosLoading = true

        viewModelScope.launch {
            ApiConstants.retrofit.getFavoriteVideos("Token ${TokenManager.getToken()}", pageSizeFavVideos, currentOffsetFavVideos, ordering).enqueue(object : retrofit2.Callback<FavoriteVideosResponse>{
                override fun onResponse(call: Call<FavoriteVideosResponse>, response: Response<FavoriteVideosResponse>) {
                    isFavoriteVideosLoading = false
                    if (!response.isSuccessful) {
                        errorMessage = response.message()
                    } else {
                        val newVideos = response.body()?.data?.results ?: emptyList()
                        favouriteVideos.addAll(newVideos)
                        currentOffsetFavVideos += newVideos.size
                    }
                }

                override fun onFailure(call: Call<FavoriteVideosResponse>, response: Throwable) {
                    isFavoriteVideosLoading = false
                    errorMessage = response.toString()
                }

            })
        }
    }
    

}

