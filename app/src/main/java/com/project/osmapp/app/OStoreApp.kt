package com.project.osmapp.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.project.osmapp.logic.SetupNavigation


@Composable
fun OStoreApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White) {
        val navController = rememberNavController()

        // Llama a la función que configura la navegación
        SetupNavigation(navController)
    }
}

