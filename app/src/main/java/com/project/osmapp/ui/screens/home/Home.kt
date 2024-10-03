package com.project.osmapp.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.project.osmapp.components.TopBarComponent
import com.project.osmapp.components.BottomNavigationBar
import com.project.osmapp.components.ScreenOrientationComponent

@Composable
fun HomeScreen() {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {

    }
}
/*Menu de navegacion*/
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBarComponent()
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                ScreenOrientationComponent()

            }
            BottomNavigationBar(navController)
        }
}

@Preview
@Composable
fun DefaultPreviewOfHomeScreen() {
    HomeScreen()
}