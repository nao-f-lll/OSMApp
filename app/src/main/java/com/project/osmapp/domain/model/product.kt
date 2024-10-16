package com.project.osmapp.domain.model

import com.google.firebase.firestore.DocumentSnapshot

data class Product(
    val nombre: String = "",
    val contador: Int = 0,
    val precio: Double = 0.0,
    val imagen: String = "",
    val id: String = ""
)