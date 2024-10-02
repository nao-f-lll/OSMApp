package com.project.osmapp.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.project.osmapp.screens.HomeScreen

@Composable
fun OStoreApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White) {
        HomeScreen()
    }
}