package com.memoittech.cuviewtv.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memoittech.cuviewtv.R
import com.memoittech.cuviewtv.ui.theme.Gray
import com.memoittech.cuviewtv.ui.theme.GrayBlue
import com.memoittech.cuviewtv.ui.theme.GrayBlueLight
import com.memoittech.cuviewtv.ui.theme.Violet
import com.memoittech.cuviewtv.ui.theme.componentShapes


@Composable
fun NormalTextComponent(value:String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = Gray,
        textAlign = TextAlign.Center
    )
}

@Composable
fun HeadingTextComponent(value:String){
    Text(
        text = value,
        modifier = Modifier.fillMaxWidth()
            .padding(0.dp, 20.dp),
        style = TextStyle(
            fontSize = 28.sp,
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Normal
        ),
        color = Color.White,
        textAlign = TextAlign.Center
    )
}

@Composable
fun MyTextFieldComponent(text:String, onTextChange: (String)->Unit, labelValue: String, imageVector: Int){

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.medium)
            .heightIn(48.dp)
            .padding(20.dp, 10.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            ),
        placeholder = {
            Text(
                text = labelValue,
                color = GrayBlueLight
            )
        },
        value = text,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Violet,
            cursorColor = GrayBlueLight
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        onValueChange = onTextChange,
        leadingIcon = {
           Image(painter = painterResource(imageVector), contentDescription = "")
        }
    )
}



@Composable
fun PasswordTextFieldComponent(text:String, onTextChange: (String)->Unit, labelValue: String, imageVector: Int){

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.medium)
            .heightIn(48.dp)
            .padding(20.dp, 10.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            ),

        placeholder = {
            Text(
                text = labelValue,
                color = GrayBlueLight
            )
        },

        value = text,

        colors = TextFieldDefaults.textFieldColors(
            textColor = Violet,
            cursorColor = GrayBlueLight
        ),

        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        singleLine = true,
        maxLines = 1,
        onValueChange = onTextChange,

        leadingIcon = {
            Image(painter = painterResource(imageVector), contentDescription = "")
        },

        trailingIcon = {
            val iconImage = if(passwordVisible.value) {
                R.drawable.eye
            } else {
                R.drawable.eye
            }

            val descripton = if(passwordVisible.value){
                stringResource(id = R.string.hide_password)
            } else {
                stringResource(id = R.string.show_password)
            }

            IconButton(onClick = {passwordVisible.value = !passwordVisible.value}) {
                Image(painter = painterResource(iconImage), contentDescription = descripton)
            }
        },

        visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()

    )
}


@Composable
fun CheckBoxComponent(
    value: String,
    checked : Boolean,
    onCheck : () -> Unit,
    onTextSelected : (String)->Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .heightIn(56.dp)
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
//        val checkedState = remember {
//            mutableStateOf(false)
//        }
        Checkbox(checked = checked,
            onCheckedChange = {
                onCheck()
            },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.White,
                uncheckedColor = Color.Gray,
                checkmarkColor = Color.White
            )
        )

        ClickableTextComponent(
            value = value,
            onTextSelected = onTextSelected
        )
    }

}

//@Preview
//@Composable
//fun previewComponent() {
//    CheckBoxComponent("value 1", {})
//}


@Composable
fun ClickableTextComponent(value: String, onTextSelected : (String)->Unit){
    val initialText = "By continuing you accept our "
    val privacyPolicyText = "privacy_policy"
    val andText = " and "
    val termsAndConditionsText = "Term of Use"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = GrayBlue)){
            pushStringAnnotation(tag = privacyPolicyText , annotation = privacyPolicyText)
            append(privacyPolicyText)
        }
        append(andText)
        withStyle(style = SpanStyle(color = GrayBlue)){
            pushStringAnnotation(tag = termsAndConditionsText , annotation = termsAndConditionsText)
            append(termsAndConditionsText)
        }
    }

    ClickableText(text = annotatedString,
        style = TextStyle(
            color = Color.White
        ),
        onClick = { offset ->
        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.also {span ->
                if ((span.item == termsAndConditionsText) || (span.item == privacyPolicyText)){
                    onTextSelected(span.item)
                }
            }
    })
}

@Composable
fun ButtonComponent(value: String, onClick : ()-> Unit, color : Color){
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp)
            .padding(20.dp, 10.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(10.dp)
            ),
            contentAlignment = Center
        ) {
            Text(text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}



@Composable
fun UnderLinedTextComponent(value:String, onClick : () -> Unit){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp)
            .padding(20.dp, 10.dp)
            .clickable {
                onClick()
            },
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = Gray,
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline,

    )
}


//@Preview
//@Composable
//fun prevBack(){
//    ImageComponent()
//}














