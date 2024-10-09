package com.project.osmapp.domain.model

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException

data class Product(
    val nombre: String = "",
    val contador: Int = 0,
    val precio: Double = 0.0,
    val imagen: String = "",
    val id: String = ""
) {


    companion object {
        fun fromDocumentSnapshot(snapshot: DocumentSnapshot): Product {
            val nombre = snapshot.getString("nombre") ?: ""
            val contador = snapshot.get("contador")?.let {
                when (it) {
                    is Long -> it.toInt()
                    is String -> it.toIntOrNull() ?: 0
                    else -> 0
                }
            } ?: 0
            val precio = snapshot.getDouble("precio") ?: 0.0
            val imagen = snapshot.getString("imagen") ?: ""
            val id = snapshot.getString("id") ?: ""
            return Product(nombre, contador, precio, imagen, id)
        }
    }
}