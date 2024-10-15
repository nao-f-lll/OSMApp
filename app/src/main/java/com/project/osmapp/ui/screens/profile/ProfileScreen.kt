package com.project.osmapp.ui.screens.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.osmapp.R
import com.project.osmapp.components.BottomNavigationBar
import com.project.osmapp.components.ProfileHeader
import com.project.osmapp.components.TopBarComponent
import com.project.osmapp.logic.AuthUtils


@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun ProfileScreen(navController: NavHostController) {

    val user = AuthUtils.getCurrentUser() // Obtiene el usuario actual
    val isLoggedIn = user != null // Verifica si el usuario ha iniciado sesión

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBarComponent()

            // Sección de perfil
            ProfileHeader(
                userName = user?.email ?: stringResource(id = R.string.unauthenticated_user),
                profileImageUrl = user?.photoUrl?.toString(),
                isLoggedIn = isLoggedIn,
                onLoginClick = {
                    navController.navigate("Login")
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Selector de idioma (aquí se puede agregar posteriormente)

            Spacer(modifier = Modifier.height(360.dp))

            // Botón de cerrar sesión si está autenticado
            if (isLoggedIn) {
                Button(
                    onClick = {
                        AuthUtils.signOut() // Llama a la función para cerrar sesión
                        navController.navigate("home")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text(
                        text = stringResource(id = R.string.logout_button),
                        color = Color.White
                    )
                }
            }
        }
    }
}

