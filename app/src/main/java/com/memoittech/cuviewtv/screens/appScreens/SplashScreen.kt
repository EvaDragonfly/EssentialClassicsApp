package com.memoittech.cuviewtv.screens.appScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.memoittech.cuviewtv.viewModel.AppViewModels
import com.memoittech.cuviewtv.viewModel.AuthViewModel

@Composable
fun SplashScreen(navController: NavHostController) {

    val viewModel :AuthViewModel = viewModel()
    val isLoading by viewModel.isLoading.collectAsState()
    val isAuthenticated by viewModel.isAuthenticated.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkAuth(navController)
    }

    when {
        isLoading -> {
            // Show splash/loading
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        isAuthenticated -> navController.navigate("main/slider") {
            popUpTo("splash") { inclusive = true }
        }
        else -> navController.navigate("auth/login") {
            popUpTo("splash") { inclusive = true }
        }
    }
}