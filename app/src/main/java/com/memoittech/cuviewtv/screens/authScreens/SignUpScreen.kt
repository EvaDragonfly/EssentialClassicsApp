package com.memoittech.cuviewtv.screens.authScreens

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.memoittech.cuviewtv.ApiConstants
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.components.ButtonComponent
import com.memoittech.cuviewtv.components.CheckBoxComponent
import com.memoittech.cuviewtv.components.HeadingTextComponent
import com.memoittech.cuviewtv.components.MyTextFieldComponent
import com.memoittech.cuviewtv.components.PasswordTextFieldComponent
import com.memoittech.cuviewtv.components.ValidationMessage
import com.memoittech.cuviewtv.components.isValidEmail
import com.memoittech.cuviewtv.components.isValidPassword
import com.memoittech.cuviewtv.model.User
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.ui.theme.Rose
import com.memoittech.cuviewtv.ui.theme.Violet
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

@Composable
fun SignUpScreen (navController: NavHostController) {

    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }

    var rePassword by remember { mutableStateOf("") }

    var checked by remember { mutableStateOf(false) }

    val user = User(email = email, password = password)

    val context = LocalContext.current

    val dialogStatus = remember {
        mutableStateOf(false)
    }

    val alertText = remember { mutableStateOf("") }


    fun signUpClickHandler( ) {
        if (!isValidEmail(email)){
            alertText.value = "Please, Enter a Valid Email"
            dialogStatus.value = true
        } else if (password != rePassword){
            alertText.value = "Passwords Don't Match"
            dialogStatus.value = true
        } else if (password.length < 8){
            alertText.value = "Password should contain 8 symbols"
            dialogStatus.value = true
        } else if (!isValidPassword(password)){
            alertText.value ="Password Should contain letters and special characters"
            dialogStatus.value = true
        } else if (!checked){
            alertText.value ="Please, agree the privacy policy"
            dialogStatus.value = true
        } else {
            ApiConstants.retrofit.createUser(user).enqueue(object : retrofit2.Callback<ResponseBody>{
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
                        navController.navigate("auth/email_confirmation/${email}")
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
        Box(modifier = Modifier.fillMaxSize()
            .background(color = DarkBg2)){
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "backgroundImage",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier.fillMaxSize()
                    .align(Alignment.Center)
                    .background(color = Color.Transparent),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .padding(0.dp, 95.dp, 0.dp, 0.dp)
                        .align(Alignment.CenterHorizontally)
                )

                HeadingTextComponent(value = stringResource(id = R.string.login_text))

                Spacer(modifier = Modifier.height(70.dp))

                ButtonComponent(
                    value = stringResource(R.string.login),
                    onClick = {
                        navController.navigate("auth/login")
                    }, Violet
                )

                MyTextFieldComponent(
                    email,
                    onTextChange = {email = it},
                    labelValue = stringResource(R.string.email),
                    imageVector = R.drawable.email
                )
                PasswordTextFieldComponent(
                    password,
                    onTextChange = {password = it},
                    labelValue = stringResource(R.string.password),
                    imageVector = R.drawable.key
                )
                PasswordTextFieldComponent(
                    rePassword,
                    onTextChange = {rePassword = it},
                    labelValue = stringResource(R.string.repeat_password),
                    imageVector = R.drawable.key
                )
                CheckBoxComponent(
                    value = stringResource(R.string.terms_and_conditions),
                    checked = checked,
                    onCheck = {
                        checked = !checked
                    },
                    onTextSelected = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://memoittech.com/privacy-policy-apps/"))
                        context.startActivity(intent)
//                        navController.navigate("auth/terms_and_conditions")
                    }
                )
//                Spacer(modifier = Modifier.height(80.dp))
                ButtonComponent(
                    value = stringResource(R.string.register),
                    onClick = {
                        signUpClickHandler()
                    }, Rose
                )
                if(dialogStatus.value){
                    ValidationMessage(
                        alertText.value,
                        dialogStatus.value,
                        onDissmis = {dialogStatus.value = true},
                        onClick = {dialogStatus.value = false}
                    )
                }
            }
        }
    }
}

