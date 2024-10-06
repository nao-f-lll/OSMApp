package com.project.osmapp.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector


sealed class NavigationItem(val route: String, val icon: ImageVector, val title: String) {
    object Home : NavigationItem("home", Icons.Default.Home, "Inicio")
    object Gallery : NavigationItem("products", Icons.Default.ShoppingCart, "Productos")
    object Contact : NavigationItem("contact", Icons.Default.Phone, "Contacto")
    object Profile : NavigationItem("profile", Icons.Default.Person, "Perfil")
}