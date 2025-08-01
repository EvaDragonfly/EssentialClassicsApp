package com.memoittech.cuviewtv.screens.authScreens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.memoittech.cuviewtv.ApiConstants
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.TokenManager
import com.memoittech.cuviewtv.components.ButtonComponent
import com.memoittech.cuviewtv.components.HeadingTextComponent
import com.memoittech.cuviewtv.components.MyTextFieldComponent
import com.memoittech.cuviewtv.components.PasswordTextFieldComponent
import com.memoittech.cuviewtv.components.UnderLinedTextComponent
import com.memoittech.cuviewtv.components.ValidationMessage
import com.memoittech.cuviewtv.components.isValidEmail
import com.memoittech.cuviewtv.components.isValidPassword
import com.memoittech.cuviewtv.model.User
import com.memoittech.cuviewtv.ui.theme.Rose
import com.memoittech.cuviewtv.ui.theme.Violet
import com.memoittech.cuviewtv.viewModel.AuthViewModel
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response


@Composable
fun LoginScreen (navController: NavHostController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val user = User(email = email, password = password)

    val context = LocalContext.current

    val scrollState = rememberScrollState()

    val sharedPreferences = remember {
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

    val dialogStatus = remember {
        mutableStateOf(false)
    }

    val alertText = remember { mutableStateOf("") }

    val authViewModel : AuthViewModel = viewModel()


    fun signInHandler(){
        if (!isValidEmail(email)){
            alertText.value = "Please, Enter a Valid Email"
            dialogStatus.value = true
        } else if (password.length < 8){
            alertText.value = "Password should contain 8 symbols"
            dialogStatus.value = true
        } else if (!isValidPassword(password)){
            alertText.value ="Password Should contain letters and special characters"
            dialogStatus.value = true
        } else {
            ApiConstants.retrofit.signIn(user).enqueue( object : retrofit2.Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(!response.isSuccessful){
                        val errorBody = response.errorBody()?.string()
                        if (!errorBody.isNullOrEmpty()) {
                            val json = JSONObject(errorBody)

                            if (json.has("error")) {
                                val errorObj = json.getJSONObject("error")

                                val allErrors = mutableListOf<String>()

                                for (key in errorObj.keys()) {
                                    val errorArray = errorObj.getJSONArray(key)
                                    for (i in 0 until errorArray.length()) {
                                        allErrors.add(errorArray.getString(i))
                                    }
                                }

                                // Use first error, or join them all
                                val firstError = allErrors.firstOrNull()
//                                val fullMessage = allErrors.joinToString("\n")
//
//                                Log.e("API_ERROR", "First: $firstError\nAll: $fullMessage")
                                alertText.value = firstError.toString()
                                dialogStatus.value = true
                            }
                        }
                        else {
                            alertText.value = "Something went wrong, Please try again"
                            dialogStatus.value = true
                        }
                    } else {
                        val key = response.body()?.string()
                            ?.let { JSONObject(it).getJSONObject("data").getString("key") }
                        TokenManager.saveToken(key.toString())
                        sharedPreferences.edit().putString("email", user.email).apply()
                        key?.let {
                            authViewModel.checkAuth(navController)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, response: Throwable) {
                    alertText.value ="Something went wrong, Please try again"
                    dialogStatus.value = true
                }

            }
            )
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)) {

            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "backgroundImage",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .background(color = Color.Transparent)
                    .verticalScroll(scrollState) // 👈 enable scrolling
                    .imePadding()
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .padding(0.dp, 95.dp, 0.dp, 0.dp)
                        .align(Alignment.CenterHorizontally)
                )
                HeadingTextComponent(value = stringResource(id = R.string.login_text))

                Spacer(modifier = Modifier.height(100.dp))

                ButtonComponent(

                    value = stringResource(R.string.register),
                    onClick = {
                        navController.navigate("auth/sign_up")
                    },
                    Violet
                )

                MyTextFieldComponent(
                    email,
                    onTextChange = { email = it },
                    labelValue = stringResource(R.string.email),
                    imageVector = R.drawable.email
                )
                PasswordTextFieldComponent(
                    password,
                    onTextChange = { password = it },
                    labelValue = stringResource(R.string.password),
                    imageVector = R.drawable.key
                )
//                Spacer(modifier = Modifier.height(20.dp))
                ButtonComponent(
                    value = stringResource(R.string.login),
                    onClick = {
                        signInHandler()
                    },
                    Rose
                )

                Spacer(modifier = Modifier.height(30.dp))

                UnderLinedTextComponent(
                    value = stringResource(id = R.string.forgot_password),
                    onClick = {
                        navController.navigate("auth/forgot_password")
                    }
                )

            }
            if (dialogStatus.value) {
                ValidationMessage(
                    alertText.value,
                    dialogStatus.value,
                    onDissmis = { dialogStatus.value = true },
                    onClick = { dialogStatus.value = false }
                )
            }
        }

    }
}

