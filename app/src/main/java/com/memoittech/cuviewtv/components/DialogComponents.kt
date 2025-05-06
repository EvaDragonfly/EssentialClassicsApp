package com.memoittech.cuviewtv.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.ui.theme.Violet
import com.memoittech.cuviewtv.viewModel.AuthViewModel




@Composable
fun ValidationMessage(message: String, dialogStatus: Boolean, onDissmis : () -> Unit, onClick: () -> Unit){

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (dialogStatus){
            AlertDialog(
                onDismissRequest = {onDissmis()},
                confirmButton = {
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {onClick()}
                    ) {
                        Text("OK", color = Violet, fontSize = 20.sp)
                    }
                },
                title = {
                    Row (verticalAlignment = Alignment.CenterVertically){
                        Icons.Filled.AddAlert
                    }
                },
                text = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = message,
                        color = Violet,
                        fontSize = 18.sp
                    )
                },
                containerColor = DarkBg2,
                shape = RoundedCornerShape(10.dp)
            )
        }

    }
}


@Composable
fun CustomDialog(
    showDialog: Boolean,
    onDissmiss: () -> Unit,
    onConfirm: () -> Unit,
    setAlertText: (Any) -> Unit
) {

    var password by remember { mutableStateOf("") }

    var rePassword by remember { mutableStateOf("") }

    var alertText by remember { mutableStateOf("") }

    val authViewModel : AuthViewModel = viewModel()


    fun passwordChangeHandler(){
        if (password != rePassword){
            alertText = "Passwords Don't Match"
//            dialogStatus.value = true
        } else if (password.length < 8){
            alertText = "Password should contain 8 symbols"
//            dialogStatus.value = true
        } else if (!isValidPassword(password)){
            alertText ="Password Should contain letters, numbers and special characters"
//            dialogStatus.value = true
        } else {
            authViewModel.changePassword(password, { onConfirm() }, { setAlertText("") })
        }
    }

    if (showDialog) {
        Dialog(onDismissRequest = { onDissmiss() }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = DarkBg2,
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Custom Dialog Title")

                    if(alertText.length>0){
                        Text(text = alertText)
                    }

                    PasswordTextFieldComponent(
                        password,
                        onTextChange = {password = it},
                        labelValue = stringResource(R.string.password),
                        imageVector = R.drawable.key
                    )
                    Spacer(Modifier.height(16.dp))
                    PasswordTextFieldComponent(
                        rePassword,
                        onTextChange = {rePassword = it},
                        labelValue = stringResource(R.string.repeat_password),
                        imageVector = R.drawable.key
                    )
                    Spacer(Modifier.height(16.dp))

                    Button(onClick = { passwordChangeHandler() }){
                        Text("Send")
                    }

                    Button(onClick = { onDissmiss() }) {
                        Text("Close")
                    }

                }
            }
        }
    }
}


//@Preview
//@Composable
//fun prevDialog(){
//    var alertText by remember { mutableStateOf("") }
//    CustomDialog(true, {}, {},  { newValue -> alertText = newValue.toString() })
//}