package com.memoittech.cuviewtv.model

data class User(
    val email : String,
    val password : String
) {
}

data class UserData(
    val id : Int,
    val email : String
){}

data class UserResponse(
    val code : Int,
    val data : UserData
){}

