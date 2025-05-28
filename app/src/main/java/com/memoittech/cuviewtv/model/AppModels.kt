package com.memoittech.cuviewtv.model

import androidx.compose.runtime.MutableState
import com.google.gson.annotations.SerializedName

data class Member(
    val id : Int,
    val title : String,
    val position : Int
) {
}

data class MembersData(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Member>
){}

data class MembersResponse(
    val code : Int,
    val data : MembersData
) {}

data class Member_Wrapper(
    val member_id : Int,
    val member_title : String
){}

data class FavouriteMember(
    val member: Member,
    val created_at: String
){}

data class FavoriteMembersData(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<FavouriteMember>
){}

data class FavouriteMembersResponse(
    val code : Int,
    val data : FavoriteMembersData
){}

data class Track(
    val id : Int,
    val title : String,
    val part : String,
    val performers : List<Member_Wrapper>,
    val composers : List<Member_Wrapper>,
    val position : Int,
    val is_favorite: Boolean
) {}

data class TracksData (
    val count : Int,
    val next : String,
    val previous :  String,
    val results : List<Track>
){}

data class TracksResponse(
    val code : Int,
    val data : TracksData
){}

data class FavoriteTrack(
    val track: Track,
    val created_at: String
){}

data class FavoriteTracksData(
    val id : Int,
    val next : String,
    val previous :  String,
    val results : List<FavoriteTrack>
){}

data class FavoriteTracksResponse(
    val code : Int,
    val data : FavoriteTracksData
){}

data class Video(
    val id: Int,
    val youtube_id: String,
    val title: String,
    val description: String,
    val duration: Long,
    val created_at: String,
    val position: Int,
    val views: Long,
    val likes: Long,
    val is_favorite: Boolean,
    val has_thumbnail: Boolean
)

data class VideosData(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Video>
)

data class VideoResponse(
    val code : Int,
    val data : VideosData
){}

data class VideoTrack(
    val track : Track,
    val position: Int,
    val starts_at: Int,
    val ends_at: Int
){}

data class VideoTrackData(
    val count: Int,
    val next: String,
    val previous: String,
    val results : List<VideoTrack>
){}

data class VideoTracksResponse(
    val code : Int,
    val data : VideoTrackData
){}

data class FavoriteVideo(
    val video : Video,
    val created_at: String
){}

data class FavoriteVideosData(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<FavoriteVideo>
){}

data class FavoriteVideosResponse(
    val code : Int,
    val data : FavoriteVideosData
){}

data class MemberDetailsData(
    val id: Int,
    val title: String,
    val biography_text : String?,
    val is_favorite : Boolean
){}

data class MemberDetailsResponse(
    val code : Int,
    val data : MemberDetailsData
){}

data class TrackWrapper(
    val track : Track
){}

data class MemberTracksData(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<TrackWrapper>
){}

data class MemberTracksResponse(
    val code: Int,
    val data: MemberTracksData
){}

data class TrackDetailsData(
    val id : Int,
    val title : String,
    val part : String,
    val duration : Int,
    val performers : List<Member_Wrapper>,
    val composers : List<Member_Wrapper>,
    val is_favorite : Boolean
){}

data class TrackDetailsResponse(
    val code : Int,
    val data : TrackDetailsData
){}

data class VideoDetailsData(
    val id: Int,
    val youtube_id: String,
    val title: String,
    val description: String,
    val duration: Int,
    val is_favorite: Boolean,
    val has_thumbnail: Boolean
){}

data class VideoDetailsResponse(
    val code : Int,
    val data : VideoDetailsData
){}

data class VideoWrapper(
    val video: Video
){}

data class MemberVideosData(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<VideoWrapper>
){}

data class MemberVideosReponse(
    val code: Int,
    val data: MemberVideosData
){}

data class Mood(
    val id : Int,
    val title: String
){}

data class TrackVideoWrapper(
    val video : Video,
    val position: Int,
    val starts_at: Int,
    val ends_at: Int
){}

data class TrackVideosData(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<TrackVideoWrapper>
){}

data class TrackVideosResponse(
    val code: Int,
    val data : TrackVideosData
){}

data class MoodTrack(
    val track : Track,
    val video : Video,
    val position: Int,
    val starts_at: Int,
    val ends_at: Int
){}

data class MoodTracksResponse(
    val code: Int,
    val data : List<MoodTrack>
){}

data class FavoriteCountData(
    val tracks : Int,
    val videos : Int,
    val albums : Int,
    val members : Int,
){}

data class FavoritesCountResponse(
    val code : Int,
    val data : FavoriteCountData
){}