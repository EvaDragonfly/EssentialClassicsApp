package com.memoittech.cuviewtv

import android.content.Context
import android.content.SharedPreferences
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConstants {
    private const val BASE_URL = "https://eclass.cugate.com"

    val retrofit: RetrofitApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(RetrofitApi ::class.java)

}

object TokenManager {
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        prefs.edit().putString("key", token).apply()
    }

    fun getToken(): String? = prefs.getString("key", null)

    fun clearToken() {
        prefs.edit().remove("key").apply()
    }
}

