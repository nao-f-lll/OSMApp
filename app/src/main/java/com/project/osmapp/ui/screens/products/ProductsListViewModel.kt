package com.project.osmapp.ui.screens.products

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.project.osmapp.domain.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductsListViewModel : ViewModel() {
    private var _productList = MutableStateFlow<List<Product>>(emptyList())
    var productList = _productList.asStateFlow()

    init {
        getProductList()
    }

    private fun getProductList() {
        val db = Firebase.firestore
        db.collection("cars")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("ProductsListViewModel", "Error fetching products", error)
                    return@addSnapshotListener
                }
                if (value != null) {
                    _productList.value = value.toObjects()
                }
            }
    }
}