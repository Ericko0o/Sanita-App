package com.example.sanitaapp.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sanitaapp.R // Asegúrate de tener R importado

/**
 * Componente Composable para la barra de navegación superior (Navbar).
 * Incluye el logo, el título y un ícono de búsqueda.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar() {
    CenterAlignedTopAppBar(
        title = {
            // Contenido de la barra superior, centrado
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Logo de la rana
                Image(
                    painter = painterResource(id = R.drawable.logo_rana), // Asegúrate de tener logo_rana.png en res/drawable
                    contentDescription = "Logo Sanita",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                // Título de la aplicación
                Text(text = "Sanita", color = Color.White, fontSize = 20.sp)
            }
        },
        actions = {
            // Ícono de búsqueda a la derecha
            IconButton(onClick = {
                // TODO: Implementar la funcionalidad de búsqueda o navegación
                Log.d("AppTopBar", "Se hizo clic en el ícono de búsqueda")
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0x80566F61), // Color de fondo con transparencia para el efecto de blur
            titleContentColor = Color.White // Color del texto
        )
    )
}