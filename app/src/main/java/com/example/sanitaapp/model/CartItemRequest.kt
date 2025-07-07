package com.example.sanitaapp.model

data class CartItemRequest(
    val usuarioId: Int,
    val plantaId: Int,
    val cantidad: Int
)