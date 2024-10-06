package com.project.osmapp.ui.screens.products

import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.project.osmapp.domain.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductsListViewModel : ViewModel() {
    private var _productList = MutableStateFlow<List<Product>>(emptyList())
    var productList = _productList.asStateFlow()

    init {
        getProductList()
    }

    private fun getProductList() {
        val db = Firebase.firestore
        db.collection("cars")
            .addSnapshotListener {value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                if (value != null) {
                    _productList.value  = value.toObjects()
                }
            }
    }
}