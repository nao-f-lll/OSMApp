package com.project.osmapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.osmapp.R

@Composable
fun TrendImageTitleComponent(value:String) {
    Text(
        text = value,
        modifier = Modifier.fillMaxWidth().heightIn(min = 80.dp),
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
        ),
        color = Color.Black,
    )
}

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
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 4.dp),
            contentDescription = stringResource(id = R.string.main_img_description),

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

