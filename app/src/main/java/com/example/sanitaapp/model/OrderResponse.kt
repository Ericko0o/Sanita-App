package com.example.sanitaapp.model

data class OrderResponse(
    val id: Int,
    val usuarioId: Int,
    val fecha: String,
    val estado: String
    // Puedes agregar más campos si los tienes (plantas incluidas, dirección, etc.)
)
