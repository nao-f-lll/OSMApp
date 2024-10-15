package com.project.osmapp.ui.screens.signin

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.osmapp.logic.AuthUtils
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException


@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // Configuración de Google Sign-In
    val context = LocalContext.current
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("1025463367649-r9olsangeb3ojngmtr4rh3i13td2d2g5.apps.googleusercontent.com")  // Reemplaza con tu cliente web OAuth de Firebase
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = AuthUtils.getIdToken(account)
                if (idToken != null) {
                    // Llama a la función de inicio de sesión con Google
                    AuthUtils.signInWithGoogle(idToken) { loginResult, error ->
                        if (loginResult?.isSuccessful == true) {
                            // Navega a la pantalla principal si el login con Google fue exitoso
                            navController.navigate("Profile")
                        } else {
                            errorMessage = "Error al iniciar sesión con Google: $error"
                        }
                    }
                }
            } catch (e: ApiException) {
                errorMessage = "Error al obtener el token de Google."
            }
        }
    }

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp)
        ) {
            Column {
                NormalTextComponent(value = "¡Hola!")
                HeadingTextComponent(value = "Bienvenido de nuevo")
            }
            Spacer(modifier = Modifier.height(50.dp))
            Column {
                MyTextFieldComponent(
                    labelValue = "Correo Electrónico",
                    icon = Icons.Outlined.Email,
                    value = email,
                    onValueChange = { email = it }
                )
                Spacer(modifier = Modifier.height(10.dp))
                PasswordTextFieldComponent(
                    labelValue = "Contraseña",
                    icon = Icons.Outlined.Lock,
                    value = password,
                    onValueChange = { password = it }
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            // Mostrar mensaje de error si existe
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red)
            }

            BottomComponent(
                textQuery = "¿No tienes una cuenta? ",
                textClickable = "Regístrate",
                action = "Iniciar Sesión",
                onClickPrimary = {
                    // Comprobar si los campos estaan vacios
                    if (email.isEmpty() || password.isEmpty()) {
                        errorMessage = "Por favor, completa todos los campos."
                    } else {
                        // Llama a la función de inicio de sesion con correo y contraseña
                        AuthUtils.loginWithEmail(email, password) { result, error ->
                            if (result?.isSuccessful == true) {
                                // Navega a la pantalla principal si el login fue exitoso
                                navController.navigate("Profile")
                            } else {
                                errorMessage = "Error al iniciar sesión, comprueba el email y la contraseña."
                            }
                        }
                    }
                },
                onClickGoogle = {
                    // Inicia el flujo de inicio de sesion de Google
                    val signInIntent = googleSignInClient.signInIntent
                    launcher.launch(signInIntent)
                },
                navController = navController
            )
        }
    }
}