package com.memoittech.cuviewtv.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memoittech.cuviewtv.ApiConstants
import com.memoittech.cuviewtv.TokenManager
import com.memoittech.cuviewtv.model.FavoriteMembersData
import com.memoittech.cuviewtv.model.FavouriteMembersResponse
import com.memoittech.cuviewtv.model.MemberDetailsData
import com.memoittech.cuviewtv.model.MemberDetailsResponse
import com.memoittech.cuviewtv.model.MemberTracksData
import com.memoittech.cuviewtv.model.MemberTracksResponse
import com.memoittech.cuviewtv.model.MemberVideosData
import com.memoittech.cuviewtv.model.MemberVideosReponse
import com.memoittech.cuviewtv.model.MembersData
import com.memoittech.cuviewtv.model.MembersResponse
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response



class MembersViewModel : ViewModel(){
    var performersResponse by mutableStateOf<MembersData?>(null)
    var composersResponse by mutableStateOf<MembersData?>(null)
    var membersResponse by mutableStateOf<MemberDetailsData?>(null)
    var favouriteMemberResponse by mutableStateOf<ResponseBody?>(null)
    var favouriteMembersResponse by mutableStateOf<FavoriteMembersData?>(null)
    var memberTracksResponse by mutableStateOf<MemberTracksData?>(null)
    var membersVideoResponse by mutableStateOf<MemberVideosData?>(null)
    var errorMessage : String by mutableStateOf("")

    fun getPerformersList(limit : Int, offset : Int, ordering : String, q : String ) {
        viewModelScope.launch {
            ApiConstants.retrofit.getPerformers(limit, offset, ordering, q, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<MembersResponse>{
                override fun onResponse(call: Call<MembersResponse>, response: Response<MembersResponse>) =
                    if(!response.isSuccessful){
                        errorMessage = response.message()
                    } else {
                        performersResponse = response.body()?.data
                    }

                override fun onFailure(call: Call<MembersResponse>, response: Throwable) {
                    errorMessage = response.toString()
                }

            })
        }
    }


    fun getComposerList(limit : Int, offset : Int, ordering : String, q : String) {
        viewModelScope.launch {
            ApiConstants.retrofit.getComposers(limit, offset, ordering, q, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<MembersResponse>{
                override fun onResponse(call: Call<MembersResponse>, response: Response<MembersResponse>) =
                    if(!response.isSuccessful){
                        errorMessage = response.message()
                    } else {
                        composersResponse = response.body()?.data
                    }

                override fun onFailure(call: Call<MembersResponse>, response: Throwable) {
                    errorMessage = response.toString()
                }

            })
        }
    }


    fun getMemberDetails(id : Int ) {
        viewModelScope.launch {
            ApiConstants.retrofit.getMember(id, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<MemberDetailsResponse>{
                override fun onResponse(call: Call<MemberDetailsResponse>, response: Response<MemberDetailsResponse>) =
                    if(!response.isSuccessful){
                        errorMessage = response.message()
                    } else {
                        membersResponse = response.body()?.data
                    }

                override fun onFailure(call: Call<MemberDetailsResponse>, response: Throwable) {
                    errorMessage = response.toString()
                }

            })
        }
    }

    fun addFavoriteMember(id : Int, delete : Boolean ) {
        viewModelScope.launch {
            ApiConstants.retrofit.addFavoriteMember(id, delete, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) =
                    if(!response.isSuccessful){
                        errorMessage = response.message()
                    } else {
                        favouriteMemberResponse = response.body()
                    }

                override fun onFailure(call: Call<ResponseBody>, response: Throwable) {
                    errorMessage = response.toString()
                }

            })
        }
    }

    fun getFavoriteMembers(limit : Int, offset : Int, ordering : String ) {
        viewModelScope.launch {
            ApiConstants.retrofit.getFavoriteMembers("Token ${TokenManager.getToken()}",limit, offset, ordering).enqueue(
                object : retrofit2.Callback<FavouriteMembersResponse> {
                    override fun onResponse(
                        call: Call<FavouriteMembersResponse>,
                        response: Response<FavouriteMembersResponse>,
                    ) =
                        if (!response.isSuccessful) {
                            errorMessage = response.message()
                        } else {
                            favouriteMembersResponse = response.body()?.data
                        }

                    override fun onFailure(
                        call: Call<FavouriteMembersResponse>,
                        response: Throwable,
                    ) {
                        errorMessage = response.toString()
                    }

                },
            )
        }
    }


    fun getMemberTracks(id : Int ) {
        viewModelScope.launch {
            ApiConstants.retrofit.getMemberTracks(id, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<MemberTracksResponse>{
                override fun onResponse(call: Call<MemberTracksResponse>, response: Response<MemberTracksResponse>) =
                    if(!response.isSuccessful){
                        errorMessage = response.message()
                    } else {
                        memberTracksResponse = response.body()?.data
                    }

                override fun onFailure(call: Call<MemberTracksResponse>, response: Throwable) {
                    errorMessage = response.toString()
                }

            })
        }
    }


    fun getMemberVideos(id : Int ) {
        viewModelScope.launch {
            ApiConstants.retrofit.getMemberVideos(id, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<MemberVideosReponse>{
                override fun onResponse(call: Call<MemberVideosReponse>, response: Response<MemberVideosReponse>) =
                    if(!response.isSuccessful){
                        errorMessage = response.message()
                    } else {
                        membersVideoResponse = response.body()?.data
                    }

                override fun onFailure(call: Call<MemberVideosReponse>, response: Throwable) {
                    errorMessage = response.toString()
                }

            })
        }
    }

}



