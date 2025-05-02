package com.memoittech.cuviewtv.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memoittech.cuviewtv.ApiConstants
import com.memoittech.cuviewtv.TokenManager
import com.memoittech.cuviewtv.model.FavoriteTracksData
import com.memoittech.cuviewtv.model.FavoriteTracksResponse
import com.memoittech.cuviewtv.model.FavoriteVideosData
import com.memoittech.cuviewtv.model.FavoriteVideosResponse
import com.memoittech.cuviewtv.model.TrackDetailsData
import com.memoittech.cuviewtv.model.TrackDetailsResponse
import com.memoittech.cuviewtv.model.TracksData
import com.memoittech.cuviewtv.model.TracksResponse
import com.memoittech.cuviewtv.model.VideoResponse
import com.memoittech.cuviewtv.model.VideosData
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class TracksViewModel : ViewModel(){

    var tracksResponse by mutableStateOf<TracksData?>(null)

    var favouriteTracksResponse by mutableStateOf<FavoriteTracksData?>(null)

    var favouriteTrackResponse by mutableStateOf<ResponseBody?>(null)

    var trackdetailResponse by mutableStateOf<TrackDetailsData?>(null)

    var trackVideosResponse by mutableStateOf<VideosData?>(null)

    var errorMessage : String by mutableStateOf("")

    fun getTracksList(limit : Int, offset : Int, ordering : String, q : String ) {
        viewModelScope.launch {
            ApiConstants.retrofit.getTracks(limit, offset, ordering, q, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<TracksResponse>{
                override fun onResponse(call: Call<TracksResponse>, response: Response<TracksResponse>) =
                    if(!response.isSuccessful){
                        errorMessage = response.message()
                    } else {
                        tracksResponse = response.body()?.data
                    }

                override fun onFailure(call: Call<TracksResponse>, response: Throwable) {
                    errorMessage = response.toString()
                }

            })
        }
    }

    fun getFavoriteTracks(limit : Int, offset : Int, ordering : String) {
        viewModelScope.launch {
            ApiConstants.retrofit.getFavoriteTracks("Token ${TokenManager.getToken()}", limit, offset, ordering).enqueue(object : retrofit2.Callback<FavoriteTracksResponse>{
                override fun onResponse(call: Call<FavoriteTracksResponse>, response: Response<FavoriteTracksResponse>) =
                    if(!response.isSuccessful){
                        errorMessage = response.message()
                    } else {
                        favouriteTracksResponse  = response.body()?.data
                    }

                override fun onFailure(call: Call<FavoriteTracksResponse>, response: Throwable) {
                    errorMessage = response.toString()
                }

            })
        }
    }

    fun addFavoriteTrack(id : Int, delete : Boolean ) {
        viewModelScope.launch {
            ApiConstants.retrofit.addFavoriteTrack(id, delete, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) =
                    if(!response.isSuccessful){
                        errorMessage = response.message()
                    } else {
                        favouriteTrackResponse = response.body()
                    }

                override fun onFailure(call: Call<ResponseBody>, response: Throwable) {
                    errorMessage = response.toString()
                }

            })
        }
    }

    fun getTrackDetails(id : Int) {
        viewModelScope.launch {
            ApiConstants.retrofit.getTrack(id, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<TrackDetailsResponse>{
                override fun onResponse(call: Call<TrackDetailsResponse>, response: Response<TrackDetailsResponse>) =
                    if(!response.isSuccessful){
                        errorMessage = response.message()
                    } else {
                        trackdetailResponse = response.body()?.data
                    }

                override fun onFailure(call: Call<TrackDetailsResponse>, response: Throwable) {
                    errorMessage = response.toString()
                }

            })
        }
    }

    fun getTrackVideos(id : Int){
        viewModelScope.launch {
            ApiConstants.retrofit.getTrackVideos(id, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<VideoResponse>{
                override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) =
                    if(!response.isSuccessful){
                        errorMessage = response.message()
                    } else {
                        trackVideosResponse = response.body()?.data
                    }

                override fun onFailure(call: Call<VideoResponse>, response: Throwable) {
                    errorMessage = response.toString()
                }

            })
        }
    }
}




