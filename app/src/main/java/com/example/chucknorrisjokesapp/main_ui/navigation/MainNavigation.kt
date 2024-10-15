package com.example.chucknorrisjokesapp.main_ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chucknorrisjokesapp.di.ControllersModule
import com.example.chucknorrisjokesapp.main_ui.MainScreen

@Composable
fun MainNavigation(controllers: ControllersModule)
{
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.MainScreen.route)
    {
        composable(Routes.MainScreen.route)
        {
            MainScreen(navController,controllers.provideMainScreenController())
        }
    }

}