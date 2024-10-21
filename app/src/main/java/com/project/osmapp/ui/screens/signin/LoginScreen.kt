package com.project.osmapp.ui.screens.signin

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.project.osmapp.R
import com.project.osmapp.components.BackArrow


@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    val idToken = AuthUtils.getIdToken(account)
                    if (idToken != null) {
                        AuthUtils.signInWithGoogle(idToken) { loginResult, error ->
                            if (loginResult?.isSuccessful == true) {
                                navController.navigate("Profile")
                            } else {
                                errorMessage =
                                    context.getString(R.string.google_signin_error) + error
                            }
                        }
                    }
                } catch (e: ApiException) {
                    errorMessage = context.getString(R.string.google_token_error)
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

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    BackArrow(
                        navController = navController,
                        modifier = Modifier.offset(x = (-29).dp)
                    )
                    Spacer(modifier = Modifier.width(1.dp))
                    NormalTextComponent(value = stringResource(id = R.string.hello))
                }


                HeadingTextComponent(value = stringResource(id = R.string.welcome_back))
            }
            Spacer(modifier = Modifier.height(50.dp))
            Column {
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.email_label),
                    icon = Icons.Outlined.Email,
                    value = email,
                    onValueChange = { email = it }
                )
                Spacer(modifier = Modifier.height(10.dp))
                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password_label),
                    icon = Icons.Outlined.Lock,
                    value = password,
                    onValueChange = { password = it }
                )
            }
            Spacer(modifier = Modifier.weight(1f))


            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red)
            }

            BottomComponent(
                textQuery = stringResource(id = R.string.no_account_question),
                textClickable = stringResource(id = R.string.sign_up),
                action = stringResource(id = R.string.login_action),
                onClickPrimary = {
                    if (email.isEmpty() || password.isEmpty()) {
                        errorMessage = context.getString(R.string.complete_all_fields)
                    } else {
                        AuthUtils.loginWithEmail(email, password) { result, error ->
                            if (result?.isSuccessful == true) {
                                navController.navigate("Profile")
                            } else {
                                errorMessage = context.getString(R.string.login_error)
                            }
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
