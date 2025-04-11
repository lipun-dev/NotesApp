package com.example.notesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.presentation.screens.HomeScreenUI

@Composable
fun App() {

    var navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = routes.Home
    ) {
        composable<routes.Home> {
            HomeScreenUI()
        }
    }

}