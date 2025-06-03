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
import com.memoittech.cuviewtv.model.FavouriteMember
import com.memoittech.cuviewtv.model.FavouriteMembersResponse
import com.memoittech.cuviewtv.model.Member
import com.memoittech.cuviewtv.model.MemberDetailsData
import com.memoittech.cuviewtv.model.MemberDetailsResponse
import com.memoittech.cuviewtv.model.MemberTracksResponse
import com.memoittech.cuviewtv.model.MemberVideosReponse
import com.memoittech.cuviewtv.model.MembersResponse
import com.memoittech.cuviewtv.model.Track
import com.memoittech.cuviewtv.model.TrackVideo
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response



class MembersViewModel : ViewModel(){
    var performers = mutableStateListOf<Member>()
    var composers = mutableStateListOf<Member>()
    var favouriteMembers = mutableStateListOf<FavouriteMember>()
    var memberTracks =  mutableStateListOf<Track>()
    var membersVideos = mutableStateListOf<TrackVideo>()


    var isComposerLoading by mutableStateOf(false)
        private set
    var isPerformerLoading by mutableStateOf(false)
        private set
    var isFavMembersLoading by mutableStateOf(false)
        private set
    var isMemberTracksLoading by mutableStateOf(false)
        private set
    var isMemberVideosLoading by mutableStateOf(false)
        private set

    private var currentComposerOffset = 0
    private val pageSizeComposers = 20

    private var currentPerformerOffset = 0
    private val pageSizePerformers = 20

    private var currentFavMembersOffset = 0
    private val pageSizeFavMembers = 20

    private var currentMemberTracksOffset = 0
    private val pageSizeMemberTracks = 20

    private var currentMemberVideosOffset = 0
    private val pageSizeMemberVideos = 20

    var membersResponse by mutableStateOf<MemberDetailsData?>(null)
    var favouriteMemberResponse by mutableStateOf<ResponseBody?>(null)
    var errorMessage : String by mutableStateOf("")

    fun getPerformersList(image_only : Int, ordering : String, q : String, index : Int ) {

        if (isPerformerLoading) return
        isPerformerLoading = true

        if(index == 0){
            currentPerformerOffset = 0
            performers.clear()
        }

        viewModelScope.launch {
            ApiConstants.retrofit.getPerformers(image_only, pageSizePerformers, currentPerformerOffset, ordering, q, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<MembersResponse>{
                override fun onResponse(call: Call<MembersResponse>, response: Response<MembersResponse>) {
                    if (!response.isSuccessful) {
                        errorMessage = response.message()
                    } else {
                        val newPerformers = response.body()?.data?.results ?: emptyList()
                        performers.addAll(newPerformers)
                        currentPerformerOffset += newPerformers.size
                    }
                    isPerformerLoading = false
                }

                override fun onFailure(call: Call<MembersResponse>, response: Throwable) {
                    isPerformerLoading = false
                    errorMessage = response.toString()
                }

            })
        }
    }


    fun getComposerList(image_only : Int, ordering : String, q : String, index : Int) {

        if (isComposerLoading) return
        isComposerLoading = true

        if(index == 0){
            currentComposerOffset = 0
            composers.clear()
        }

        viewModelScope.launch {
            ApiConstants.retrofit.getComposers(image_only, pageSizeComposers, currentComposerOffset, ordering, q, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<MembersResponse>{
                override fun onResponse(call: Call<MembersResponse>, response: Response<MembersResponse>) {

                    if (!response.isSuccessful) {
                        errorMessage = response.message()
                    } else {
                        val newComposers = response.body()?.data?.results ?: emptyList()
                        composers.addAll(newComposers)
                        currentComposerOffset += newComposers.size
                    }
                    isComposerLoading = false
                }

                override fun onFailure(call: Call<MembersResponse>, response: Throwable) {
                    isComposerLoading = false
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

    fun getFavoriteMembers( ordering : String ) {
        if (isFavMembersLoading) return
        isFavMembersLoading = true

        viewModelScope.launch {
            ApiConstants.retrofit.getFavoriteMembers("Token ${TokenManager.getToken()}", pageSizeFavMembers, currentFavMembersOffset, ordering).enqueue(
                object : retrofit2.Callback<FavouriteMembersResponse> {
                    override fun onResponse(
                        call: Call<FavouriteMembersResponse>,
                        response: Response<FavouriteMembersResponse>,
                    ) {
                        isFavMembersLoading = false
                        if (!response.isSuccessful) {
                            errorMessage = response.message()
                        } else {
                            val newMembers = response.body()?.data?.results ?: emptyList()
                            favouriteMembers.addAll(newMembers)
                            currentFavMembersOffset += newMembers.size
                        }
                    }

                    override fun onFailure(
                        call: Call<FavouriteMembersResponse>,
                        response: Throwable,
                    ) {
                        isFavMembersLoading = false
                        errorMessage = response.toString()
                    }

                },
            )
        }
    }


    fun getMemberTracks(id : Int ) {

        if(isMemberTracksLoading) return
        isMemberTracksLoading = true

        viewModelScope.launch {
            ApiConstants.retrofit.getMemberTracks(id, pageSizeMemberTracks, currentMemberTracksOffset,"Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<MemberTracksResponse>{
                override fun onResponse(call: Call<MemberTracksResponse>, response: Response<MemberTracksResponse>) {
                    if (!response.isSuccessful) {
                        isMemberTracksLoading = false
                        errorMessage = response.message()
                    } else {
                        val newTracks = response.body()?.data?.results ?: emptyList()
                        memberTracks.addAll(newTracks)
                        currentMemberTracksOffset += newTracks.size
                    }
                }

                override fun onFailure(call: Call<MemberTracksResponse>, response: Throwable) {
                    isMemberTracksLoading = false
                    errorMessage = response.toString()
                }

            })
        }
    }


    fun getMemberVideos(id : Int ) {

        if ( isMemberVideosLoading ) return
        isMemberVideosLoading = true

        viewModelScope.launch {
            ApiConstants.retrofit.getMemberVideos(id, pageSizeMemberVideos, currentMemberVideosOffset, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<MemberVideosReponse>{
                override fun onResponse(call: Call<MemberVideosReponse>, response: Response<MemberVideosReponse>) {
                    isMemberVideosLoading = false
                    if (!response.isSuccessful) {
                        errorMessage = response.message()
                    } else {
                        val newVideos = response.body()?.data?.results ?: emptyList()
                        membersVideos.addAll(newVideos)
                        currentMemberVideosOffset += newVideos.size
                    }
                }

                override fun onFailure(call: Call<MemberVideosReponse>, response: Throwable) {
                    isMemberVideosLoading = false
                    errorMessage = response.toString()
                }

            })
        }
    }
}



