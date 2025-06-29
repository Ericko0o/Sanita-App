package com.example.sanitaapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.weight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

/**
 * Barra de navegación inferior personalizada para la aplicación SanitaApp.
 *
 * @param navController El controlador de navegación que permite cambiar de pantallas.
 */
@Composable
fun AppBottomNavigationBar(navController: NavController) {
    BottomAppBar(
        containerColor = Color(0xFF132e17), // Fondo oscuro
        contentColor = Color.White,
        modifier = Modifier.height(70.dp)
    ) {
        // Inicio
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            IconButton(onClick = {
                navController.navigate("home_screen") {
                    launchSingleTop = true
                }
            }) {
                Icon(Icons.Default.Home, contentDescription = "Inicio")
            }
            Text("Inicio", fontSize = 12.sp)
        }

        // Catálogo
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            IconButton(onClick = {
                navController.navigate("catalogo_screen") {
                    launchSingleTop = true
                }
            }) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Catálogo")
            }
            Text("Catálogo", fontSize = 12.sp)
        }

        // Comunidad
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            IconButton(onClick = {
                navController.navigate("comunidad_screen") {
                    launchSingleTop = true
                }
            }) {
                Icon(Icons.Default.Person, contentDescription = "Comunidad")
            }
            Text("Comunidad", fontSize = 12.sp)
        }

        // Noticias
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            IconButton(onClick = {
                navController.navigate("noticias_screen") {
                    launchSingleTop = true
                }
            }) {
                Icon(Icons.Default.Info, contentDescription = "Noticias")
            }
            Text("Noticias", fontSize = 12.sp)
        }
    }
}
