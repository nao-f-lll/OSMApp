package com.project.osmapp.components


import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.project.osmapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun TopBarComponent() {
    Box(modifier = Modifier
        .height(80.dp)
        .fillMaxWidth()
        .background(Color.White),
    contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.olanyeta_full_size_logo),
            contentDescription = stringResource(id = R.string.main_img_description),
            modifier = Modifier
                .size(200.dp)
                .padding(top = 30.dp),
            )
    }
}

@Composable
fun ScreenOrientationComponent() {
   val config = LocalConfiguration.current
    if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
        TrendImageListComponentPortrateLayout()
    }
}

@Composable
fun TrendImageListComponentPortrateLayout(modifier: Modifier = Modifier) {

    val images = listOf(
        R.drawable.trend_image_1,
        R.drawable.trend_image_2,
        R.drawable.trend_image_3,
        R.drawable.trend_image_4,
        R.drawable.trend_image_5
    )

    val texts = listOf(
        stringResource(id = R.string.img_trend_1_title_es),
        stringResource(id = R.string.img_trend_2_title_es),
        stringResource(id = R.string.img_trend_3_title_es),
        stringResource(id = R.string.img_trend_4_title_es),
        stringResource(id = R.string.img_trend_5_title_es)
    )
    val pagerState = rememberPagerState(
        pageCount =
        { images.size }
    )
    LaunchedEffect(Unit) {
        while (true) {
            delay(4000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }
    val scope = rememberCoroutineScope()

    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = modifier.wrapContentSize()) {
            HorizontalPager(
                state = pagerState,
                modifier .fillMaxWidth(0.9f)
                    .height(480.dp)
            ) { currentPage ->
                Card(
                    modifier
                        .wrapContentSize()
                        .padding(26.dp)
                        .height(480.dp) ,
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Box {
                        Image(
                            painter = painterResource(id = images[currentPage]),
                            contentDescription = "",
                            contentScale = ContentScale.Crop

                        )
                        Box(
                            modifier = modifier
                                .background(Color.Black.copy(alpha = 0.1f))
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = texts[currentPage],
                                modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(12.dp),
                                color = Color.White,
                                style = MaterialTheme.typography.headlineLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            IconButton(
                onClick = {
                    val nextPage = pagerState.currentPage + 1
                    if (nextPage < images.size) {
                        scope.launch {
                            pagerState.scrollToPage(nextPage)
                        }
                    }
                },
                modifier
                    .padding(30.dp)
                    .size(48.dp)
                    .align(Alignment.CenterEnd)
                    .clip(CircleShape),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0x52373737)
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "",
                    modifier.fillMaxSize(),
                    tint = Color.LightGray
                )
            }
            IconButton(
                onClick = {
                    val prevPage = pagerState.currentPage -1
                    if (prevPage >= 0) {
                        scope.launch {
                            pagerState.scrollToPage(prevPage)
                        }
                    }
                },
                modifier
                    .padding(30.dp)
                    .size(48.dp)
                    .align(Alignment.CenterStart)
                    .clip(CircleShape),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0x52373737)
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "",
                    modifier.fillMaxSize(),
                    tint = Color.LightGray
                )
            }
        }

        PageIndicator(
            pageCount = images.size,
            currentPage = pagerState.currentPage,
            modifier = modifier
        )

    }
}


@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount){
            IndicatorDots(isSelected = it == currentPage, modifier= modifier)
        }
    }
}

@Composable
fun IndicatorDots(isSelected: Boolean, modifier: Modifier) {
    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
    Box(modifier = modifier.padding(2.dp)
        .size(size.value)
        .clip(CircleShape)
        .background(if (isSelected) Color(0xff373737) else Color(0xA8373737))
    )
}


@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Gallery,
        NavigationItem.Contact,
        NavigationItem.Profile
    )

    NavigationBar {
        // Obtener la ruta actual
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        // Iterar sobre los elementos de navegación
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = stringResource(item.title)) },
                label = { Text(text = stringResource(item.title)) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}


/*Componentes de la pagina de contacto*/

@Composable
fun StoreDescription() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Text(
            text =    stringResource(id = R.string.store_description),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Black,
                fontSize = 16.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}


@Composable
fun SocialMediaIconsComponent(modifier: Modifier) {
    val context = LocalContext.current

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Instagram
        Image(
            painter = painterResource(id = R.drawable.instagram_icon),
            contentDescription = "Instagram",
            modifier = Modifier
                .size(48.dp)
                .clickable {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/olanetaoficial")
                    )
                    context.startActivity(intent)
                }
        )
        // Teléfono
        Image(
            painter = painterResource(id = R.drawable.phone_icon),
            contentDescription = "Teléfono",
            modifier = Modifier
                .size(48.dp)
                .clickable {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel: 944 15 31 96"))
                    context.startActivity(intent)
                }
        )
        // Correo
        Image(
            painter = painterResource(id = R.drawable.email_icon),
            contentDescription = "Correo",
            modifier = Modifier
                .size(48.dp)
                .clickable {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:info@xabierolaneta.com")
                    }
                    context.startActivity(intent)
                }
        )
        // Sitio web
        Image(
            painter = painterResource(id = R.drawable.website_icon),
            contentDescription = "",
            modifier = Modifier
                .size(48.dp)
                .clickable {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://olañeta.com")
                    )
                    context.startActivity(intent) }
        )
    }
}

@Composable
fun MapsWebView() {
    val context = LocalContext.current
    val mapUrl = "https://maps.app.goo.gl/ubNRtbXrNuXp9vdp6"

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.store_map),
            contentDescription = "",
            modifier = Modifier
                .size(350.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl))
                    context.startActivity(intent)
                },
            contentScale = ContentScale.Crop
        )
    }
}

/*Componetes de la pagina de perfil*/

@Composable
fun ProfileHeader(
    userName: String,
    profileImageUrl: String?,
    isLoggedIn: Boolean,
    onLoginClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (profileImageUrl != null) {
            // Imagen de perfil del usuario
            Image(
                painter = rememberAsyncImagePainter(model = profileImageUrl),
                contentDescription = stringResource(id = R.string.content_description_profile_picture),
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            // Imagen genérica si no tiene una foto de perfil
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = stringResource(id = R.string.content_description_generic_picture),
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                tint = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Texto con el nombre o mensaje genérico
        Text(
            text = if (isLoggedIn) {
                stringResource(id = R.string.welcome_user, userName)
            } else {
                stringResource(id = R.string.login_prompt)
            },
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall
        )

        // Botón de iniciar sesión si no está autenticado
        if (!isLoggedIn) {
            Text(
                text = stringResource(id = R.string.login_button),
                modifier = Modifier
                    .clickable { onLoginClick() }
                    .padding(top = 8.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Marcas(modifier: Modifier = Modifier) {
    // Lista de imágenes
    val images = listOf(
        R.drawable.garcia,
        R.drawable.car,
        R.drawable.lylu,
        R.drawable.hoff
    )

    val pagerState = rememberPagerState(pageCount = { images.size })

    LaunchedEffect(pagerState) {
        while (true) {
            delay(4000)
            val nextPage = (pagerState.currentPage + 1) % images.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = modifier.wrapContentSize()) {
            HorizontalPager(
                state = pagerState,
                modifier = modifier
                    .fillMaxWidth(0.6f)
                    .height(300.dp)
            ) { currentPage ->

                val scale by animateFloatAsState(
                    targetValue = if (pagerState.currentPage == currentPage) 1f else 0.9f
                )
                val alpha by animateFloatAsState(
                    targetValue = if (pagerState.currentPage == currentPage) 1f else 0.5f
                )

                Card(
                    modifier = Modifier
                        .size(300.dp)
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            alpha = alpha
                        )
                ) {
                    Image(
                        painter = painterResource(id = images[currentPage]),
                        contentDescription = "",
                        modifier = Modifier.size(150.dp),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        }
    }
}
