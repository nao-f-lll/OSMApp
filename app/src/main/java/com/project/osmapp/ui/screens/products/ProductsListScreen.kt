package com.project.osmapp.ui.screens.products

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.project.osmapp.components.BottomNavigationBar
import com.project.osmapp.components.TopBarComponent
import com.project.osmapp.domain.model.Product
import com.project.osmapp.domain.model.MiniFabItems
import kotlinx.coroutines.tasks.await

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun ProductsListScreen(navController: NavHostController, viewModel: ProductsListViewModel = viewModel()) {
    val products by viewModel.productList.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val isConnected = remember { mutableStateOf(checkInternetConnection(context)) }
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "unknown"
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Advertencia") },
            text = { Text("Debes iniciar sesión para agregar productos a tu lista de favoritos.") },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cerrar")
                }
            }
        )
    }

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
                        GridItem(it, userId, showDialog, setShowDialog = { showDialog = it })
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
fun GridItem(product: Product, userId: String, showDialog: Boolean, setShowDialog: (Boolean) -> Unit) {
    var isLiked by remember { mutableStateOf(false) }
    var contador by remember { mutableIntStateOf(product.contador) }

    LaunchedEffect(userId, product.id) {
        isLiked = checkIfProductIsLiked(userId, product.id)
    }

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
            text = product.nombre,
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
                    if (userId == "unknown") {
                        setShowDialog(true)
                    } else {
                        isLiked = !isLiked
                        if (isLiked) {
                            addProductToFavorites(userId, product.id)
                        } else {
                            removeProductFromFavorites(userId, product.id)
                        }
                    }
                }
        )
    }
}


fun addProductToFavorites(userId: String, productId: String) {
    val db = FirebaseFirestore.getInstance()
    val favorite = hashMapOf("productId" to productId)
    db.collection("usuario-productos").document(userId)
        .collection("favoritos").document(productId)
        .set(favorite)
}

fun removeProductFromFavorites(userId: String, productId: String) {
    val db = FirebaseFirestore.getInstance()
    db.collection("usuario-productos").document(userId)
        .collection("favoritos").document(productId)
        .delete()
}

suspend fun checkIfProductIsLiked(userId: String, productId: String): Boolean {
    val db = FirebaseFirestore.getInstance()
    val document = db.collection("usuario-productos").document(userId)
        .collection("favoritos").document(productId).get().await()
    return document.exists()
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