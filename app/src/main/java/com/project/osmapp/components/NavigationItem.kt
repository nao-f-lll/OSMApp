package com.project.osmapp.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.project.osmapp.R


sealed class NavigationItem(val route: String, val icon: ImageVector, val title: Int) {
    object Home : NavigationItem("home", Icons.Default.Home, R.string.nav_title_home)
    object Gallery : NavigationItem("products", Icons.Default.ShoppingCart, R.string.nav_title_gallery)
    object Contact : NavigationItem("contact", Icons.Default.Phone, R.string.nav_title_contact)
    object Profile : NavigationItem("profile", Icons.Default.Person, R.string.nav_title_profile)
}