package com.example.chucknorrisjokesapp.main_ui.navigation

object Routes
{
    object MainScreen : Screen ("main_screen")

}

sealed class Screen(val route : String)