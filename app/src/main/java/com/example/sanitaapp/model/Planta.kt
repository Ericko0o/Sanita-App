package com.example.sanitaapp.model

data class Planta(
    val id: Int,
    val nombre: String,
    val imagen: String,
    val precio: Int, // Precio
    val categoria: Int, // Nueva campo para la categoría
)