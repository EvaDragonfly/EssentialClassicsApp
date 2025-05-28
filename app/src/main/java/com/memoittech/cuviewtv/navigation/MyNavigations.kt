package com.memoittech.cuviewtv.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.memoittech.cuviewtv.screens.MoodScreens.MoodDetailsScreen
import com.memoittech.cuviewtv.screens.SliderScreens.OpenScreen
import com.memoittech.cuviewtv.screens.authScreens.EmailConfirmationScreen
import com.memoittech.cuviewtv.screens.authScreens.ForgotPasswordScreen
import com.memoittech.cuviewtv.screens.authScreens.LoginScreen
import com.memoittech.cuviewtv.screens.authScreens.PasswordResetScreen
import com.memoittech.cuviewtv.screens.authScreens.SignUpScreen
import com.memoittech.cuviewtv.screens.authScreens.TermsAndConditionsScreen
import com.memoittech.cuviewtv.screens.appScreens.MainScreen
import com.memoittech.cuviewtv.screens.MoodScreens.MoodsScreen
import com.memoittech.cuviewtv.screens.appScreens.PlayerScreen
import com.memoittech.cuviewtv.screens.searchScreens.SearchScreen
import com.memoittech.cuviewtv.screens.appScreens.SplashScreen
import com.memoittech.cuviewtv.screens.detailScreens.MemberDetailsScreen
import com.memoittech.cuviewtv.screens.favoritesScreens.FavoriteArtistsScreen
import com.memoittech.cuviewtv.screens.favoritesScreens.FavoriteTracksScreen
import com.memoittech.cuviewtv.screens.favoritesScreens.FavoriteVideosScreen
import com.memoittech.cuviewtv.screens.favoritesScreens.FavoritesScreen
import com.memoittech.cuviewtv.screens.trackScreens.TrackScreen


@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyNavigations(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash" ){

        composable(route = "splash"){
            SplashScreen(navController)
        }

        composable(route = "main"){
            MainScreen(navController)
        }

        composable( route = "favourites"){
            FavoritesScreen(navController)
        }

        composable(route = "moods"){
            MoodsScreen(navController)
        }

        composable(
            route = "mood_details/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ){ backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            id?.let { MoodDetailsScreen( id = it, navController) }
        }

        composable(
            route = "player/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ){ backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id")
                id?.let { PlayerScreen(navController, id = it) }
        }

        composable(
            route = "search/{index}",
            arguments = listOf(
                navArgument("index") { type = NavType.IntType }
            )
        ){backStackEntry ->
            val id = backStackEntry.arguments?.getInt("index")
            id?.let { SearchScreen(navController, index = it) }
        }

        composable( route = "slider" ){
            OpenScreen(navController)
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

        composable( route = "sign_up" ){
            SignUpScreen(navController)
        }

        composable( route = "login" ){
            LoginScreen(navController)
        }

        composable( route = "terms_and_conditions" ){
            TermsAndConditionsScreen(navController)
        }

        composable(
            route = "email_confirmation/{email}" ,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ){ navBackStackEntry ->
            val email = navBackStackEntry.arguments?.getString("email")
            email?.let { EmailConfirmationScreen(navController, it) }
        }

        composable( route = "forgot_password" ){
            ForgotPasswordScreen(navController)
        }

        composable(
            route = "password_reset/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ){navBackStackEntry ->
            val email = navBackStackEntry.arguments?.getString("email")
            email?.let { PasswordResetScreen(navController, it) }
        }

    }

}