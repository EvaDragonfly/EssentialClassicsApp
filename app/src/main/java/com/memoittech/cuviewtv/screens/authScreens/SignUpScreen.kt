package com.memoittech.cuviewtv.screens.authScreens

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
import retrofit2.Call
import retrofit2.Response

@Composable
fun SignUpScreen (navController: NavController) {

    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }

    var rePassword by remember { mutableStateOf("") }

    val user = User(email = email, password = password)

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
            alertText.value ="Password Should contain letters, numbers and special characters"
            dialogStatus.value = true
        } else {
            ApiConstants.retrofit.createUser(user).enqueue(object : retrofit2.Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(!response.isSuccessful){
                        alertText.value = "Something went wrong, Please try again"
                        dialogStatus.value = true
                    } else {
                        navController.navigate("email_confirmation/${email}")
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
                        navController.navigate("login")
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
                    onTextSelected = {
                        navController.navigate("terms_and_conditions")
                    }
                )
//                Spacer(modifier = Modifier.height(80.dp))
                ButtonComponent(
                    value = stringResource(R.string.register),
                    onClick = {
                        signUpClickHandler()
                    }, Rose
                )
//                Spacer(modifier = Modifier.height(40.dp))
//                ClickableLoginTextComponent(
//                    tryingToLogin = true,
//                    onTextSelected = {
//                        navController.navigate("login")
//                    }
//                )
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

