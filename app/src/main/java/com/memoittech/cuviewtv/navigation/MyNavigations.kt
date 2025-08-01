package com.memoittech.cuviewtv.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.memoittech.cuviewtv.screens.MoodScreens.MoodDetailsScreen
import com.memoittech.cuviewtv.screens.authScreens.EmailConfirmationScreen
import com.memoittech.cuviewtv.screens.authScreens.ForgotPasswordScreen
import com.memoittech.cuviewtv.screens.authScreens.LoginScreen
import com.memoittech.cuviewtv.screens.authScreens.PasswordResetScreen
import com.memoittech.cuviewtv.screens.authScreens.SignUpScreen
import com.memoittech.cuviewtv.screens.appScreens.MainScreen
import com.memoittech.cuviewtv.screens.appScreens.PlayerScreen
import com.memoittech.cuviewtv.screens.appScreens.SplashScreen
import com.memoittech.cuviewtv.screens.memberScreens.MemberDetailsScreen
import com.memoittech.cuviewtv.screens.favoritesScreens.FavoriteArtistsScreen
import com.memoittech.cuviewtv.screens.favoritesScreens.FavoriteTracksScreen
import com.memoittech.cuviewtv.screens.favoritesScreens.FavoriteVideosScreen
import com.memoittech.cuviewtv.screens.trackScreens.TrackScreen
import com.memoittech.cuviewtv.viewModel.AppViewModels


@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyNavigations(){

    val appViewModel : AppViewModels = viewModel()

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash" ){

        composable(route = "splash"){
            SplashScreen(navController)
        }

        composable(
            route = "main/{tab}",
            arguments = listOf(
                navArgument("tab") {
                    type = NavType.StringType
                }
            )
        ){backStackEntry ->
            val tab = backStackEntry.arguments?.getString("tab") ?: "slider"
            MainScreen(navController, appViewModel, tab)
        }

        composable(
            route = "mood_details/{id}/{title}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("title") { type = NavType.StringType }
            )
        ){ backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            val title = backStackEntry.arguments?.getString("title")
            id?.let { MoodDetailsScreen(
                id = it,
                title = title.toString(),
                navController
            ) }
        }

        composable(
            route = "player/{id}/{starts_at}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("starts_at") { type = NavType.IntType }
            )
        ){ backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id")
                val starts_at = backStackEntry.arguments?.getInt("starts_at")
                id?.let { starts_at?.let { it1 -> PlayerScreen(navController, id = it, starts_at = it1) } }
        }

        composable(
            route = "member_details/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            id?.let { MemberDetailsScreen(navController, id = it) }
        }

        composable(
            route = "track_details/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            id?.let { TrackScreen(id = it, navController) }
        }

        composable( route = "favorite_members" ){
            FavoriteArtistsScreen(navController)
        }

        composable( route = "favorite_videos" ){
            FavoriteVideosScreen(navController)
        }

        composable( route = "favorite_tracks" ){
            FavoriteTracksScreen(navController)
        }

        composable( route = "auth/sign_up" ){
            SignUpScreen(navController)
        }

        composable( route = "auth/login" ){
            LoginScreen(navController)
        }

        composable(
            route = "auth/email_confirmation/{email}" ,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ){ navBackStackEntry ->
            val email = navBackStackEntry.arguments?.getString("email")
            email?.let { EmailConfirmationScreen(navController, it) }
        }

        composable( route = "auth/forgot_password" ){
            ForgotPasswordScreen(navController)
        }

        composable(
            route = "auth/password_reset/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ){navBackStackEntry ->
            val email = navBackStackEntry.arguments?.getString("email")
            email?.let { PasswordResetScreen(navController, it) }
        }

    }

}