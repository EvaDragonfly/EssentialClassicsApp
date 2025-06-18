package com.memoittech.cuviewtv

import com.memoittech.cuviewtv.model.FavoriteTracksResponse
import com.memoittech.cuviewtv.model.FavoriteVideosResponse
import com.memoittech.cuviewtv.model.FavoritesCountResponse
import com.memoittech.cuviewtv.model.FavouriteMembersResponse
import com.memoittech.cuviewtv.model.MemberDetailsResponse
import com.memoittech.cuviewtv.model.MemberTracksResponse
import com.memoittech.cuviewtv.model.MemberVideosReponse
import com.memoittech.cuviewtv.model.MembersResponse
import com.memoittech.cuviewtv.model.MoodTracksResponse
import com.memoittech.cuviewtv.model.TrackDetailsResponse
import com.memoittech.cuviewtv.model.TrackVideosResponse
import com.memoittech.cuviewtv.model.TracksResponse
import com.memoittech.cuviewtv.model.User
import com.memoittech.cuviewtv.model.UserResponse
import com.memoittech.cuviewtv.model.VideoDetailsResponse
import com.memoittech.cuviewtv.model.VideoResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApi {

    @POST("/api/v1/auth/registration/")
    fun createUser(@Body user: User) : Call<ResponseBody>

    @FormUrlEncoded
    @POST("/api/v1/auth/registration/resend-email/")
    fun resendEmail(@Field("email") email: String) : Call<ResponseBody>

    @POST("/api/v1/auth/login/")
    fun signIn(@Body user: User) : Call<ResponseBody>

    @POST("/api/v1/auth/logout/")
    fun logout(@Header("Authorization") token : String) : Call<ResponseBody>

    @GET("/api/v1/auth/user/")
    fun checkUser(@Header("Authorization") token : String) : Call<UserResponse>

    @FormUrlEncoded
    @POST("/api/v1/auth/password/reset/")
    fun resetPassword(@Field("email") email: String) : Call<ResponseBody>

    @FormUrlEncoded
    @POST("/api/v1/auth/password/change/")
    fun changePassword(
        @Field("password") password: String,
        @Header("Authorization") token : String
    ) : Call<ResponseBody>


    @GET("/api/v1/ec_playlists/performers/")
    fun getPerformers(
        @Query("image_only") image_only: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("ordering") ordering : String,
        @Query("q") q : String,
        @Header("Authorization") token : String
    ) : Call<MembersResponse>

    @GET("/api/v1/ec_playlists/composers/")
    fun getComposers(
        @Query("image_only") image_only: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("ordering") ordering : String,
        @Query("q") q : String,
        @Header("Authorization") token : String
    ) : Call<MembersResponse>

    @GET("/api/v1/ec_playlists/tracks/")
    fun getTracks(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("ordering") ordering : String,
        @Query("q") q : String,
        @Header("Authorization") token : String
    ) : Call<TracksResponse>

    @GET("/api/v1/ec_playlists/videos/")
    fun getVideos(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("ordering") ordering : String,
        @Query("q") q : String,
        @Header("Authorization") token : String
    ) : Call<VideoResponse>

    @GET("/api/v1/ec_playlists/members/{id}/")
    fun getMember(
        @Path("id") id: Int,
        @Header("Authorization") token : String
    ) : Call<MemberDetailsResponse>

    @GET("/api/v1/ec_playlists/members/{id}/tracks/")
    fun getMemberTracks(
        @Path("id") id: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Header("Authorization") token : String
    ) : Call<MemberTracksResponse>

    @GET("/api/v1/ec_playlists/members/{id}/videos/")
    fun getMemberVideos(
        @Path("id") id: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Header("Authorization") token : String
    ) : Call<MemberVideosReponse>

    @GET("/api/v1/ec_playlists/tracks/{id}/")
    fun getTrack(
        @Path("id") id: Int,
        @Header("Authorization") token : String
    ) : Call<TrackDetailsResponse>

    @GET("/api/v1/ec_playlists/tracks/{id}/videos/")
    fun getTrackVideos(
        @Path("id") id: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Header("Authorization") token : String
    ) : Call<TrackVideosResponse>

    @GET("/api/v1/ec_playlists/videos/{id}/")
    fun getVideo(
        @Path("id") id: Int,
        @Header("Authorization") token : String
    ) : Call<VideoDetailsResponse>


    @FormUrlEncoded
    @POST("/api/v1/ec_playlists/favorites/members/")
    fun addFavoriteMember(
        @Field("id") id: Int,
        @Field("delete") delete: Boolean,
        @Header("Authorization") token : String
    ) : Call<ResponseBody>

    @FormUrlEncoded
    @POST("/api/v1/ec_playlists/favorites/tracks/")
    fun addFavoriteTrack(
        @Field("id") id: Int,
        @Field("delete") delete: Boolean,
        @Header("Authorization") token : String
    ) : Call<ResponseBody>

    @FormUrlEncoded
    @POST("/api/v1/ec_playlists/favorites/videos/")
    fun addFavoriteVideo(
        @Field("id") id: Int,
        @Field("delete") delete: Boolean,
        @Header("Authorization") token : String
    ) : Call<ResponseBody>

    @GET("/api/v1/ec_playlists/favorites/tracks/")
    fun getFavoriteTracks(
        @Header("Authorization") token : String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("ordering") ordering : String,
    ) : Call<FavoriteTracksResponse>

    @GET("/api/v1/ec_playlists/favorites/videos/")
    fun getFavoriteVideos(
        @Header("Authorization") token : String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("ordering") ordering : String,
    ) : Call<FavoriteVideosResponse>

    @GET("/api/v1/ec_playlists/favorites/members/")
    fun getFavoriteMembers(
        @Header("Authorization") token : String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("ordering") ordering : String,
    ) : Call<FavouriteMembersResponse>


    @GET("/api/v1/ec_playlists/mood_tracks/{moodId}/")
    fun getMoodTracks(
        @Header("Authorization") token : String,
        @Path("moodId") moodId : Int
    ) : Call<MoodTracksResponse>

    @GET("/api/v1/ec_playlists/favorites_count/")
    fun getFavoriteCount(
        @Header("Authorization") token : String
    ) : Call<FavoritesCountResponse>
}


