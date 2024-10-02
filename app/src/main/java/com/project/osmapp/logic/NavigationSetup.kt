package com.project.osmapp.logic


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.osmapp.components.NavigationItem
import com.project.osmapp.screens.*


@Composable
fun SetupNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Home.route
    ) {
        composable(NavigationItem.Home.route) { HomeScreen(navController) }
        composable(NavigationItem.Gallery.route) { GalleryScreen(navController) }
        composable(NavigationItem.Contact.route) { ContactScreen(navController) }
        composable(NavigationItem.Profile.route) { ProfileScreen(navController) }
    }
}