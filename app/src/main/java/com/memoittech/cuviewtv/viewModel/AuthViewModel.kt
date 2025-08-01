package com.memoittech.cuviewtv.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.memoittech.cuviewtv.ApiConstants
import com.memoittech.cuviewtv.TokenManager
import com.memoittech.cuviewtv.model.UserData
import com.memoittech.cuviewtv.model.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response


class AuthViewModel(application : Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

    val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    var user by mutableStateOf<UserData?>(null)

    fun checkAuth(navController: NavHostController) {
        viewModelScope.launch {
            val token = TokenManager.getToken()
            if (token.isNullOrEmpty()) {
                _isAuthenticated.value = false
                _isLoading.value = false
            } else {
                try {
                   ApiConstants.retrofit.checkUser("Token $token")
                       .enqueue(object : retrofit2.Callback<UserResponse> {

                           override fun onResponse(
                               call: Call<UserResponse>,
                               response: retrofit2.Response<UserResponse>
                           ) {
                               if (!response.isSuccessful) {
                                   Log.d("MY_TAG", "first auth")
                                   Log.d("MY_TAG", response.message())
                                   Log.d("MY_TAG", response.errorBody().toString())
                                   Log.d("MY_TAG", response.body().toString())
                                   Log.d("MY_TAG", response.body().toString())
                                   navController.navigate("auth/login")
                               } else {
                                   user = response.body()?.data
                                   navController.navigate("main/slider"){
                                       popUpTo(0) {
                                           inclusive = true
                                       }
                                   }
                               }
                           }

                           override fun onFailure(call: Call<UserResponse>, response: Throwable) {
                               navController.navigate("auth/login")
                           }

                       })
                    _isAuthenticated.value = true
                } catch (e: Exception) {
                    _isAuthenticated.value = false
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }



    fun logout(navController: NavHostController){
        viewModelScope.launch {
            ApiConstants.retrofit.logout("Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(!response.isSuccessful){
                        navController.navigate("main/slider")
                    } else {
                        TokenManager.clearToken()
                        prefs.edit().remove("email").apply()
                        navController.navigate("auth/login"){
                            popUpTo(0){
                                inclusive = true
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, response: Throwable) {
                    navController.navigate("main/slider")
                }

            })
        }
    }

    fun changePassword(password : String, onClick : ()->Unit, setAlertMessage: (String) -> Unit ){
        viewModelScope.launch {
            ApiConstants.retrofit.changePassword(password, "Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(!response.isSuccessful){
                        setAlertMessage("Password is not changed")
                    } else {
                        setAlertMessage("Password has changed")
                        onClick()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, response: Throwable) {
                }

            })
        }
    }
}

