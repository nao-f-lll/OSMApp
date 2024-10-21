package com.project.osmapp.ui.screens.signup

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.project.osmapp.R
import com.project.osmapp.components.BackArrow
import com.project.osmapp.logic.AuthUtils
import com.project.osmapp.ui.screens.signin.BottomComponent
import com.project.osmapp.ui.screens.signin.CheckboxComponent
import com.project.osmapp.ui.screens.signin.HeadingTextComponent
import com.project.osmapp.ui.screens.signin.MyTextFieldComponent
import com.project.osmapp.ui.screens.signin.NormalTextComponent
import com.project.osmapp.ui.screens.signin.PasswordTextFieldComponent

@Composable
fun SignupScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val activity = LocalContext.current as Activity


    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(LocalContext.current, gso)

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    val idToken = account.idToken // Obtiene el ID token
                    if (idToken != null) {
                        // Llama a la función de inicio de sesión con Google
                        AuthUtils.signInWithGoogle(idToken) { loginResult, error ->
                            if (loginResult?.isSuccessful == true) {
                                navController.navigate("Profile")
                            } else {
                                errorMessage =
                                    "Error al iniciar sesión con Google: ${error ?: "Desconocido"}"
                            }
                        }
                    } else {
                        errorMessage = "El ID token es nulo."
                    }
                } catch (e: ApiException) {
                    errorMessage = "Error al obtener el token de Google: ${e.message}"
                }
            } else {
                errorMessage = "Resultado de inicio de sesión con Google no fue exitoso."
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
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                BackArrow(
                    navController = navController,
                    modifier = Modifier.offset(x = (-16).dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                NormalTextComponent(value = stringResource(id = R.string.signup_welcome))
            }

            HeadingTextComponent(value = stringResource(id = R.string.signup_title))
            Spacer(modifier = Modifier.height(20.dp))


            MyTextFieldComponent(
                labelValue = stringResource(id = R.string.signup_name),
                icon = Icons.Outlined.Person,
                value = name,
                onValueChange = { name = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            MyTextFieldComponent(
                labelValue = stringResource(id = R.string.signup_surname),
                icon = Icons.Outlined.Person,
                value = surname,
                onValueChange = { surname = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            MyTextFieldComponent(
                labelValue = stringResource(id = R.string.signup_email),
                icon = Icons.Outlined.Email,
                value = email,
                onValueChange = { email = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            PasswordTextFieldComponent(
                labelValue = stringResource(id = R.string.signup_password),
                icon = Icons.Outlined.Lock,
                value = password,
                onValueChange = { password = it }
            )

            // Mostrar mensaje de error si existe
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red)
            }

            CheckboxComponent()
            Spacer(modifier = Modifier.height(4.dp))

            BottomComponent(
                textQuery = stringResource(id = R.string.signup_already_have_account),
                textClickable = stringResource(id = R.string.signup_login),
                action = stringResource(id = R.string.signup_register),
                onClickPrimary = {
                    if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        errorMessage = "Por favor, completa todos los campos."
                        return@BottomComponent
                    }

                    AuthUtils.registerWithEmail(email, password) { result, error ->
                        if (result != null && result.isSuccessful) {
                            navController.navigate("Profile")
                        } else {
                            errorMessage = "Error al registrarse: $error"
                        }
                    }
                },
                onClickGoogle = {
                    val signInIntent = googleSignInClient.signInIntent
                    launcher.launch(signInIntent)
                },
                navController = navController
            )
        }
    }
}
