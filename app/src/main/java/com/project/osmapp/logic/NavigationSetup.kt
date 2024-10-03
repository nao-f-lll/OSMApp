package com.project.osmapp.logic


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.osmapp.components.NavigationItem
import com.project.osmapp.ui.screens.contact.ContactScreen
import com.project.osmapp.ui.screens.gallery.GalleryScreen
import com.project.osmapp.ui.screens.home.HomeScreen
import com.project.osmapp.ui.screens.profile.ProfileScreen


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