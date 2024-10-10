package com.project.osmapp.ui.screens.products

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.FirebaseFirestore
import com.project.osmapp.components.BottomNavigationBar
import com.project.osmapp.components.TopBarComponent
import com.project.osmapp.domain.model.Product
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import com.project.osmapp.domain.model.MiniFabItems

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun ProductsListScreen(navController: NavHostController, viewModel: ProductsListViewModel = viewModel()) {
    val products by viewModel.productList.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val isConnected = remember { mutableStateOf(checkInternetConnection(context)) }

    Scaffold(
        topBar = { TopBarComponent() },
        floatingActionButton = {
            FloatActionHandler()
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        if (isConnected.value) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                columns = GridCells.Fixed(2)
            ) {
                items(products) { product ->
                    product?.let {
                        GridItem(it)
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = com.project.osmapp.R.drawable.connection_lost_icon),
                    contentDescription = "connection lost Icon",
                    modifier = Modifier
                        .size(50.dp)
                )
            }
        }
    }
}

@Composable
fun GridItem(product: Product) {
    var isLiked by remember { mutableStateOf(false) }
    var contador by remember { mutableStateOf(product.contador) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val painter = rememberAsyncImagePainter(model = product.imagen)
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = product.nombre ?: "",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${product.precio}€",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = if (isLiked) rememberVectorPainter(Icons.Default.Favorite) else rememberVectorPainter(
                Icons.Default.FavoriteBorder
            ),
            contentDescription = "Like Icon",
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    isLiked = !isLiked
                    if (isLiked) {
                        contador += 1
                        updateContadorInFirestore(product.id, contador)
                    }
                }
        )
    }
}

fun updateContadorInFirestore(productId: String, newContador: Int) {
    val db = FirebaseFirestore.getInstance()
    db.collection("products").document(productId)
        .update("contador", newContador)
        .addOnSuccessListener {

        }
        .addOnFailureListener { e ->

        }
}
fun checkInternetConnection(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else -> false
    }
}

@Composable
fun FloatActionHandler(viewModel: ProductsListViewModel = viewModel()) {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf(
        MiniFabItems(Icons.Filled.Person, "Hombre"),
        MiniFabItems(Icons.Filled.Person, "Mujer"),
        MiniFabItems(Icons.Filled.Face, "Niña"),
        MiniFabItems(Icons.Filled.Face, "Niño")
    )
    Column(horizontalAlignment = Alignment.End) {
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }) + expandVertically(),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it }) + shrinkVertically()
        ) {
            LazyColumn(Modifier.padding(bottom = 8.dp)) {
                items(items.size) { index ->
                    val item = items[index]
                    ItemUi(icon = item.icon, title = item.title) {
                        viewModel.updateCategory(item.title.lowercase())
                        expanded = false
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        val transition = updateTransition(targetState = expanded, label = "transition")
        val rotation by transition.animateFloat(label = "rotation") {
            if (it) 315f else 0f
        }

        FloatingActionButton(
            onClick = { expanded = !expanded },
            containerColor = Color.LightGray
        ) {
            Icon(
                imageVector = Icons.Filled.Face, contentDescription = "",
                modifier = Modifier.rotate(rotation)
            )
        }
    }
}

@Composable
fun ItemUi(icon: ImageVector, title: String, onClick: () -> Unit) {
    val context = LocalContext.current
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .border(1.dp, Color.Gray, RoundedCornerShape(24.dp))
                .padding(1.dp)
                .background(Color.LightGray, CircleShape)
        ) {
            Text(text = title)
        }
        Spacer(modifier = Modifier.width(10.dp))
        FloatingActionButton(onClick = {
            onClick()
            Toast.makeText(context, title, Toast.LENGTH_SHORT).show()
        }, modifier = Modifier.size(45.dp), containerColor = Color.LightGray) {
            Icon(imageVector = icon, contentDescription = "")
        }
    }
}