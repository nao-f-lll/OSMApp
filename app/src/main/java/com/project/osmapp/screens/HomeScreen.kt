package com.project.osmapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.project.osmapp.components.TopBarComponent
import com.project.osmapp.components.TrendImageComponent
import com.project.osmapp.components.TrendImageListComponent

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBarComponent()
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            TrendImageListComponent()
        }
    }
}

@Preview
@Composable
fun DefaultPreviewOfHomeScreen() {
    HomeScreen()
}