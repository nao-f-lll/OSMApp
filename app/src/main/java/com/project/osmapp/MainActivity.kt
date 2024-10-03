package com.project.osmapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.project.osmapp.app.OStoreApp
import com.project.osmapp.ui.theme.OSMAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OSMAppTheme {
                OStoreApp()
            }
        }
    }
}
@Preview
@Composable
fun AppPreview() {
    OStoreApp()
}
