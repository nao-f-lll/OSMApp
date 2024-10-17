package com.project.osmapp.ui.screens.profile

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.project.osmapp.R
import com.project.osmapp.components.BottomNavigationBar
import com.project.osmapp.components.ProfileHeader
import com.project.osmapp.components.TopBarComponent
import com.project.osmapp.logic.AuthUtils
import com.project.osmapp.logic.LanguageViewModel
import com.project.osmapp.logic.LanguageViewModelFactory
import com.project.osmapp.logic.restartActivity

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun ProfileScreen(navController: NavHostController) {
    // Obtener el contexto y crear el factory para el ViewModel
    val context = LocalContext.current
    val factory = remember { LanguageViewModelFactory(context) }

    // Crear el ViewModel usando viewModel() con el factory
    val languageViewModel: LanguageViewModel = viewModel(factory = factory)

    // Estado del idioma actual
    val currentLanguage by languageViewModel.language.collectAsState()

    // Estado para forzar la recomposición
    var languageChanged by remember { mutableStateOf(false) }

    // Obtenemos el usuario actual
    val user = AuthUtils.getCurrentUser()
    val isLoggedIn = user != null

    // Usar LaunchedEffect para escuchar el cambio de idioma
    LaunchedEffect(currentLanguage) {
        // Forzar la recomposición cuando cambie el idioma
        languageChanged = true
    }


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
                userName = user?.email?.substringBefore("@") ?: stringResource(id = R.string.unauthenticated_user),
                profileImageUrl = user?.photoUrl?.toString(),
                isLoggedIn = isLoggedIn,
                onLoginClick = {
                    navController.navigate("Login")
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(text = stringResource(id = R.string.select_language))
            Row {
                Button(onClick = { languageViewModel.setLanguage("es") }) {
                    Text(text = "Español")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { languageViewModel.setLanguage("en") }) {
                    Text(text = "English")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { languageViewModel.setLanguage("eu") }) {
                    Text(text = "Euskara")
                }
            }
            Spacer(modifier = Modifier.height(280.dp))

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

