package com.project.osmapp.ui.screens.contact

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.project.osmapp.components.BottomNavigationBar
import com.project.osmapp.components.TopBarComponent


@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun ContactScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBarComponent()
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {

        }
        BottomNavigationBar(navController)
    }
}