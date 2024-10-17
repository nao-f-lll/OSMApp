package com.project.osmapp.ui.screens.signin

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.project.osmapp.R
import com.project.osmapp.ui.theme.AccentColor
import com.project.osmapp.ui.theme.BgColor
import com.project.osmapp.ui.theme.GrayColor
import com.project.osmapp.ui.theme.Primary
import com.project.osmapp.ui.theme.Secondary
import com.project.osmapp.ui.theme.TextColor


@Composable
fun NormalTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = TextColor,
        textAlign = TextAlign.Center
    )
}


@Composable
fun HeadingTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ),
        color = TextColor,
        textAlign = TextAlign.Center
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextFieldComponent(labelValue: String, icon: ImageVector, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        label = {
            Text(text = labelValue)
        },
        value = value,
        onValueChange = { onValueChange(it) }, // Llama a onValueChange
        colors = TextFieldDefaults.colors(
            focusedContainerColor = BgColor,
            focusedLabelColor = AccentColor,
            focusedLeadingIconColor = AccentColor,
            focusedTextColor = TextColor,
            focusedIndicatorColor = AccentColor,
            cursorColor = Primary
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "perfil"
            )
        },
        keyboardOptions = KeyboardOptions.Default
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextFieldComponent(labelValue: String, icon: ImageVector, value: String, onValueChange: (String) -> Unit) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        label = {
            Text(text = labelValue)
        },
        value = value,
        onValueChange = { onValueChange(it) }, // Llama a onValueChange
        colors = TextFieldDefaults.colors(
            focusedContainerColor = BgColor,
            focusedLabelColor = AccentColor,
            focusedLeadingIconColor = AccentColor,
            focusedTextColor = TextColor,
            focusedIndicatorColor = AccentColor,
            cursorColor = Primary
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "perfil"
            )
        },
        trailingIcon = {
            val iconImage = if (isPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
            val description = if (isPasswordVisible) "Mostrar Contraseña" else "Ocultar Contraseña"
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}


@Composable
fun CheckboxComponent() {
    var isChecked by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked = it }
        )
        ClickableTextComponent()
    }
}


@Composable
fun ClickableTextComponent() {
    val context = LocalContext.current  // Obtiene el contexto

    // Textos traducidos para cada idioma
    val initialText = stringResource(id = R.string.clickable_text_initial)
    val privacyPolicyText = stringResource(id = R.string.clickable_text_privacy_policy)
    val andText = stringResource(id = R.string.clickable_text_and)
    val termOfUseText = stringResource(id = R.string.clickable_text_terms_of_use)


    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Black)) {
            append(initialText)
        }
        withStyle(style = SpanStyle(color = Color.Blue)) {
            pushStringAnnotation(tag = "privacy_policy", annotation = "https://olañeta.com/politica-privacidad-aviso-legal/")
            append(privacyPolicyText)
            pop()
        }
        withStyle(style = SpanStyle(color = Color.Black)) {
            append(andText)
        }
        withStyle(style = SpanStyle(color = Color.Blue)) {
            pushStringAnnotation(tag = "terms_of_use", annotation = "https://olañeta.com/politica-privacidad-aviso-legal/")
            append(termOfUseText)
            pop()
        }
        append(".")
    }

    // Componente de texto clicable
    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            val annotations = annotatedString.getStringAnnotations(offset, offset)
            if (annotations.isNotEmpty()) {
                val annotation = annotations.first()
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                context.startActivity(intent)
            }
        }
    )
}




@Composable
fun BottomComponent(
    textQuery: String,
    textClickable: String,
    action: String,
    onClickPrimary: () -> Unit,    // Lambda para el botón principal
    onClickGoogle: () -> Unit,     // Lambda para el botón de Google
    navController: NavHostController
) {

    val insets = WindowInsets.navigationBars.asPaddingValues()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(insets),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Botón principal de acción
            Button(
                onClick = onClickPrimary, // Botón principal usa su propio onClick
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(listOf(Secondary, AccentColor)),
                            shape = RoundedCornerShape(50.dp)
                        )
                        .fillMaxWidth()
                        .heightIn(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = action, color = Color.White, fontSize = 20.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Divisores
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    thickness = 1.dp,
                    color = GrayColor
                )
                Text(
                    text = stringResource(id = R.string.or_text),
                    modifier = Modifier.padding(5.dp),
                    fontSize = 20.sp
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    thickness = 1.dp,
                    color = GrayColor
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Botón de Google que ocupa todo el ancho
            Button(
                onClick = onClickGoogle, // Botón de Google usa su propio onClick
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(listOf(Secondary, AccentColor)),
                            shape = RoundedCornerShape(50.dp)
                        )
                        .fillMaxWidth()
                        .heightIn(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.google_svg),
                            contentDescription = stringResource(id = R.string.google_logo_desc),
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = stringResource(id = R.string.login_with_google), color = Color.White, fontSize = 20.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Componente para consultar la cuenta
            AccountQueryComponent(textQuery, textClickable, navController)

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}






@Composable
fun AccountQueryComponent(
    textQuery: String,
    textClickable: String,
    navController: NavHostController
) {
    val annonatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = TextColor, fontSize = 15.sp)) {
            append(textQuery)
        }
        withStyle(style = SpanStyle(color = Secondary, fontSize = 15.sp)) {
            pushStringAnnotation(tag = textClickable, annotation = textClickable)
            append(textClickable)
        }
    }

    ClickableText(text = annonatedString, onClick = {
        annonatedString.getStringAnnotations(it, it)
            .firstOrNull()?.also { annonation ->
                if (annonation.item == "Iniciar sesión" ||
                    annonation.item == "Saioa Hasi" ||
                    annonation.item == "Login") {
                    navController.popBackStack() // Cerrar la actividad actual
                    navController.navigate("Login")
                } else if (annonation.item == "Regístrate" ||
                    annonation.item == "Erregistratu" ||
                    annonation.item == "Register") {
                    navController.popBackStack() // Cerrar la actividad actual
                    navController.navigate("Signup")
                }
            }
    })
}
