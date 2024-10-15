package com.example.chucknorrisjokesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chucknorrisjokesapp.di.MyApp
import com.example.chucknorrisjokesapp.main_ui.navigation.MainNavigation
import com.example.chucknorrisjokesapp.ui.theme.ChuckNorrisJokesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val coreSupervisor = (application as MyApp).cs

            val controllers = with(coreSupervisor.ApplicationMain())
            {
                getAppControllers()
            }
            MainNavigation(controllers)
        }
    }
}
