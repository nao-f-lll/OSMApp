package com.project.osmapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.osmapp.components.BottomNavigationBar
import com.project.osmapp.components.MapsWebView
import com.project.osmapp.components.SocialMediaIconsComponent
import com.project.osmapp.components.StoreDescription
import com.project.osmapp.components.TopBarComponent

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun ContactScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBarComponent()

        // Contenido principal: Descripción y mapa
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            StoreDescription()

            Spacer(modifier = Modifier.height(16.dp))

            MapsWebView()
        }

        // Iconos de redes sociales
        SocialMediaIconsComponent(Modifier)

        BottomNavigationBar(navController)
    }
}