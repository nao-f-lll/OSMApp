package com.project.osmapp.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.project.osmapp.R
import com.project.osmapp.components.TopBarComponent
import com.project.osmapp.components.TrendImageTitleComponent

@Composable
fun HomeScreen() {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        TopBarComponent()
    }
}

@Preview
@Composable
fun DefaultPreviewOfHomeScreen() {
    HomeScreen()
}