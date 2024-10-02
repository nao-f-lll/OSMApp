package com.project.osmapp.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.project.osmapp.R


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
                .padding(bottom = 4.dp),
            )
    }
}

@Composable
fun TrendImageListComponent() {
    val images = listOf(
        R.drawable.trend_image_1,
        R.drawable.trend_image_1,
        R.drawable.trend_image_1
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        items(images) { imageRes ->
            TrendImageComponent(imageRes)
        }
    }
}

@Composable
fun TrendImageComponent(imageRes: Int) {
    Box (
        modifier = Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(24.dp))
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center

    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            painter = painterResource(id = imageRes),
            contentDescription = "palceholder",
        )
        Text(
            text = "placeholder",
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(12.dp),
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
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