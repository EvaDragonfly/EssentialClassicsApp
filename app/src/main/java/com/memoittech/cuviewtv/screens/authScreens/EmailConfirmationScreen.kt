package com.memoittech.cuviewtv.screens.authScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.memoittech.cuviewtv.ApiConstants
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.components.ButtonComponent
import com.memoittech.cuviewtv.components.ImageComponent
import com.memoittech.cuviewtv.components.NormalTextComponent
import com.memoittech.cuviewtv.components.UnderLinedTextComponent
import com.memoittech.cuviewtv.components.ValidationMessage
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.ui.theme.Violet
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

@Composable
fun EmailConfirmationScreen(navController: NavController, email : String) {

    val dialogStatus = remember {
        mutableStateOf(false)
    }

    val alertText = remember { mutableStateOf("") }

    fun resendEmailHandler(){
        ApiConstants.retrofit.resendEmail(email = email).enqueue(
            object : retrofit2.Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(!response.isSuccessful){
                        alertText.value = "Something went wrong, Please try again"
                        dialogStatus.value = true
                    } else {
                        alertText.value = "Please, check an email"
                        dialogStatus.value = true
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, response: Throwable) {
                    alertText.value ="Something went wrong, Please try again"
                    dialogStatus.value = true
                }

            }
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.fillMaxSize()
                .background(color = DarkBg2)){
                ImageComponent()
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NormalTextComponent(value = "Please, check your email to verify")
                    Spacer(modifier = Modifier.height(20.dp))
                    UnderLinedTextComponent(
                        value = stringResource(id = R.string.resend_email),
                        onClick = {
                            resendEmailHandler()
                        }
                    )
                    ButtonComponent(
                        value = "Sign In",
                        onClick = {
                            navController.navigate("login")
                        },
                        Violet
                    )
                }
            }
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