package com.memoittech.cuviewtv.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memoittech.cuviewtv.ApiConstants
import com.memoittech.cuviewtv.TokenManager
import com.memoittech.cuviewtv.model.FavoriteTrack
import com.memoittech.cuviewtv.model.FavoriteTracksResponse
import com.memoittech.cuviewtv.model.Track
import com.memoittech.cuviewtv.model.TrackDetailsData
import com.memoittech.cuviewtv.model.TrackDetailsResponse
import com.memoittech.cuviewtv.model.TrackVideo
import com.memoittech.cuviewtv.model.TrackVideosResponse
import com.memoittech.cuviewtv.model.TracksResponse
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class TracksViewModel : ViewModel(){

    var tracks = mutableStateListOf<Track>()
    var favouriteTracks = mutableStateListOf<FavoriteTrack>()
    var trackVideos = mutableStateListOf<TrackVideo?>()

    var isLoading = false
    private var currentOffsetTracks = 0
    private val pageSizeTracks = 20

    var isFavTracksLoading = false
    private var currentOffsetFavTracks = 0
    private val pageSizeFavTracks = 20

    var isTrackVideosLoading = false
    private var currentOffsetTrackVideos = 0
    private val pageSizeTrackVideos = 20

    var favouriteTrackResponse by mutableStateOf<ResponseBody?>(null)

    var trackdetailResponse by mutableStateOf<TrackDetailsData?>(null)

    var errorMessage : String by mutableStateOf("")

    fun getTracksList(ordering : String, q : String, index : Int ) {

        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            ApiConstants.retrofit.getTracks(pageSizeTracks, currentOffsetTracks, ordering, q, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<TracksResponse>{
                override fun onResponse(call: Call<TracksResponse>, response: Response<TracksResponse>) {
                    isLoading = false
                    if (!response.isSuccessful) {
                        errorMessage = response.message()
                    } else {
                        val newTracks = response.body()?.data?.results ?: emptyList()
                        if (index == 1){
                            tracks.addAll(newTracks)
                            currentOffsetTracks += tracks.size
                        } else {
                            tracks.clear()
                            currentOffsetTracks = 0
                            tracks.addAll(newTracks)
                        }
                    }

                }
                override fun onFailure(call: Call<TracksResponse>, response: Throwable) {
                    isLoading = false
                    errorMessage = response.toString()
                }

            })
        }
    }

    fun getFavoriteTracks( ordering : String) {

        if (isFavTracksLoading) return
        isFavTracksLoading = true

        viewModelScope.launch {
            ApiConstants.retrofit.getFavoriteTracks("Token ${TokenManager.getToken()}", pageSizeFavTracks, currentOffsetFavTracks, ordering).enqueue(object : retrofit2.Callback<FavoriteTracksResponse>{
                override fun onResponse(call: Call<FavoriteTracksResponse>, response: Response<FavoriteTracksResponse>) {
                    isFavTracksLoading = false
                    if (!response.isSuccessful) {
                        errorMessage = response.message()
                    } else {
                        val newTracks = response.body()?.data?.results ?: emptyList()
                        favouriteTracks.addAll(newTracks)
                        currentOffsetFavTracks += newTracks.size
                    }
                }
                override fun onFailure(call: Call<FavoriteTracksResponse>, response: Throwable) {
                    isFavTracksLoading = false
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

        if (isTrackVideosLoading) return
        isTrackVideosLoading = true

        viewModelScope.launch {
            ApiConstants.retrofit.getTrackVideos(id, pageSizeTrackVideos, currentOffsetTrackVideos, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<TrackVideosResponse>{
                override fun onResponse(call: Call<TrackVideosResponse>, response: Response<TrackVideosResponse>) {
                    isTrackVideosLoading = false
                    if (!response.isSuccessful) {
                        errorMessage = response.message()
                    } else {
                        val newTracks = response.body()?.data?.results ?: emptyList()
                        trackVideos.addAll(newTracks)
                        currentOffsetTrackVideos += newTracks.size
                    }
                }

                override fun onFailure(call: Call<TrackVideosResponse>, response: Throwable) {
                    isTrackVideosLoading = false
                    errorMessage = response.toString()
                }

            })
        }
    }
}




