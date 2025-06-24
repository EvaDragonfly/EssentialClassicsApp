package com.memoittech.cuviewtv.screens.authScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.memoittech.cuviewtv.ApiConstants
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.components.ButtonComponent
import com.memoittech.cuviewtv.components.MyTextFieldComponent
import com.memoittech.cuviewtv.components.NormalTextComponent
import com.memoittech.cuviewtv.components.isValidEmail
import com.memoittech.cuviewtv.ui.theme.Violet
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

@Composable
fun ForgotPasswordScreen(navController: NavHostController) {

    var email by remember { mutableStateOf("") }

    val dialogStatus = remember {
        mutableStateOf(false)
    }

    val alertText = remember { mutableStateOf("") }

    fun passwordReset( ) {
        if (!isValidEmail(email)){
            alertText.value = "Please, Enter a Valid Email"
            dialogStatus.value = true
        } else {

            ApiConstants.retrofit.resetPassword(email).enqueue(object : retrofit2.Callback<ResponseBody>{
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
                        navController.navigate("auth/password_reset/${email}")
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, response: Throwable) {
                    alertText.value ="Something went wrong, Please try again"
                    dialogStatus.value = true
                }

            })

        }
    }


    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(color = Color.Transparent)
        ){
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "backgroundImage",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                NormalTextComponent(value = stringResource(id = R.string.forgot_password))
                Spacer(modifier = Modifier.height(20.dp))

                MyTextFieldComponent(
                    email,
                    onTextChange = {email = it},
                    labelValue = stringResource(R.string.email),
                    imageVector = R.drawable.email
                )
                Spacer(modifier = Modifier.height(20.dp))
                ButtonComponent(
                    value = stringResource(R.string.change_password),
                    onClick = {
                        passwordReset()
                    }, Violet
                )
            }
        }

    }

}