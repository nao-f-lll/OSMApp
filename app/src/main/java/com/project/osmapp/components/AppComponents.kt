package com.project.osmapp.components


import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.project.osmapp.R
import kotlinx.coroutines.delay


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
                .padding(top = 40.dp),
            )
    }
}


@Composable
fun ScreenOrientationComponent() {
   val config = LocalConfiguration.current
    if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
        TrendImageListComponentPortrateLayout()
    } else {
        TrendImageListComponentLandScapeLayout()
    }
}

@Composable
fun TrendImageListComponentLandScapeLayout(modifier: Modifier = Modifier) {
    val images = listOf(
        R.drawable.trend_image_1,
        R.drawable.trend_image_2,
        R.drawable.trend_image_3,
        R.drawable.trend_image_4
    )

    val pagerState = rememberPagerState(pageCount = { images.size })
    //logic part need to be moved to logic package
    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }

    val config = LocalConfiguration.current
    val imageSize = if (config.screenWidthDp > 600) {
        400.dp
    } else {
        200.dp
    }

    Box(
        modifier = modifier.wrapContentSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier.wrapContentWidth()
        ) { currentPage ->
            Row(
                modifier = modifier.wrapContentSize()
            ) {
                Card(
                    modifier = modifier
                        .size(imageSize)
                        .padding(20.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Box(
                        modifier = modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = images[currentPage]),
                            contentDescription = "",
                            modifier = modifier.fillMaxSize()
                        )
                        Box(
                            modifier = modifier
                                .background(Color.Black.copy(alpha = 0.1f))
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Andam",
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
                Card(
                    modifier = modifier
                        .size(imageSize)
                        .padding(20.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Box(
                        modifier = modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = images[(currentPage + 1) % images.size]),
                            contentDescription = "",
                            modifier = modifier.fillMaxSize()
                        )

                        Box(
                            modifier = modifier
                                .background(Color.Black.copy(alpha = 0.1f))
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Andam",
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
        }
    }
}

@Composable
fun TrendImageListComponentPortrateLayout(modifier: Modifier = Modifier) {
    val images = listOf(
        R.drawable.trend_image_1,
        R.drawable.trend_image_2,
        R.drawable.trend_image_3,
        R.drawable.trend_image_4
    )
    val pagerState = rememberPagerState( pageCount =
    { images.size }
        )
    //logic part need to be moved to logic package
    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
            HorizontalPager(
                state = pagerState,
            ) { currentPage ->
                Card(
                    modifier
                        .padding(26.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Box(
                        modifier = modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = images[currentPage]),
                            contentDescription = "",
                            modifier = modifier.fillMaxSize()
                        )
                        Box(
                            modifier = modifier
                                .background(Color.Black.copy(alpha = 0.1f))
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                        ) {
                        Text(
                            text = "Andam",
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
    }
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
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
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