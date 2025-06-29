package com.example.sanitaapp.ui

import androidx.compose.foundation.background // Esta es la importación necesaria para Modifier.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp // Asegúrate de que .dp también esté importado
import androidx.compose.ui.unit.sp

/**
 * Pantalla de detalle de la planta (placeholder).
 * Recibirá el ID de la planta para mostrar su información.
 */
@Composable
fun DetailScreen(plantaId: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), // Fondo para que el texto sea visible
        contentAlignment = Alignment.Center
    ) {
        // En una implementación real, aquí harías una llamada a la API
        // para obtener los detalles de la planta con el `plantaId`
        Text(
            text = "Detalle de la planta ID: $plantaId",
            color = Color.Black,
            fontSize = 20.sp
        )
    }
}
