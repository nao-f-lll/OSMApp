package com.project.osmapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.osmapp.components.BottomNavigationBar


@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun ContactScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = "Pantalla de Contacto", modifier = Modifier.padding(16.dp))
        }
    }
}
