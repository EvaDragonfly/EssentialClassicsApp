package com.memoittech.cuviewtv.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column

fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    return email.matches(emailRegex.toRegex())
}

fun isValidPassword(password: String): Boolean {
    val regex = Regex("^(?=.*[A-Za-z])(?=.*[!@#\$%^&*(),.?\":{}|<>']).+$")
    return password.matches(regex)
}

fun formatSecondsToTime(seconds: Int): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, secs)
}


fun shareLink(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, url)
    }
    val chooser = Intent.createChooser(intent, "Share Link via")
    context.startActivity(chooser)
}