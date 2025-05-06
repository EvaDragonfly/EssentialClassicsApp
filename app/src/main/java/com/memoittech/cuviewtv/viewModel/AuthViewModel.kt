package com.memoittech.cuviewtv.viewModel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
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

    fun checkAuth(navController: NavController) {
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
                                   navController.navigate("login")
                               } else {
                                   user = response.body()?.data
                                   navController.navigate("main")
                               }
                           }

                           override fun onFailure(call: Call<UserResponse>, response: Throwable) {
                               navController.navigate("login")
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



    fun logout(navController: NavController){
        viewModelScope.launch {
            ApiConstants.retrofit.logout("Token ${TokenManager.getToken()}").enqueue(object : retrofit2.Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(!response.isSuccessful){
                        navController.navigate("main")
                    } else {
                        TokenManager.clearToken()
                        prefs.edit().remove("email").apply()
                        navController.navigate("login")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, response: Throwable) {
                    navController.navigate("main")
                    navController.navigate("main")
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

