package com.project.osmapp.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.project.osmapp.R
import com.project.osmapp.components.TopBarComponent
import com.project.osmapp.components.BottomNavigationBar
import com.project.osmapp.components.Marcas
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
                .weight(0.9f)
                .fillMaxWidth()
        ) {
            ScreenOrientationComponent()

        }
        Spacer(modifier = Modifier.height(6.dp)) // Añade un espacio de 16dp de alto

        Text(
            text = stringResource(id = R.string.premium_brands),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Black,
                fontSize = 20.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Box(
            modifier = Modifier
                .weight(0.2f)
                .fillMaxWidth(0.6f)  // Mantén el 60% del ancho
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Marcas()
        }



        BottomNavigationBar(navController)
    }
}

@Preview
@Composable
fun DefaultPreviewOfHomeScreen() {
    HomeScreen()
}
