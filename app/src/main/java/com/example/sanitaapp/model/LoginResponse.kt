package com.example.sanitaapp.model

data class LoginResponse(
    val mensaje: String,
    val usuario: Usuario?
)

data class Usuario(
    val id: Int,
    val nombre: String,
    val rol: String
)