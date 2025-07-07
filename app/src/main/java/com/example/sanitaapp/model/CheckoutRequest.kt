package com.example.sanitaapp.model

data class CheckoutRequest(
    val usuarioId: Int,
    val tarjetaNumero: String,
    val tarjetaMes: Int,
    val tarjetaAno: Int,
    val tarjetaCvv: String,
    val direccion: String,
    val numero: String,
    val dni: String
)
