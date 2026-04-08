package com.leon.kidspoints.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun MainNavigation(padding: PaddingValues) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToGifts = { navController.navigate("gifts") },
                onNavigateToHistory = { navController.navigate("history") },
                onNavigateToSettings = { navController.navigate("settings") }
            )
        }
        composable("gifts") {
            GiftsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("history") {
            HistoryScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("settings") {
            SettingsScreen(
                onNavigateToRules = { navController.navigate("rules") },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("rules") {
            RulesScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
