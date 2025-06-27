package com.example.sanitaapp

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.sanitaapp.api.ApiClient
import com.example.sanitaapp.model.Planta
import kotlinx.coroutines.launch

@Composable
fun PlantasScreen(modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    var plantas by remember { mutableStateOf<List<Planta>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                plantas = ApiClient.apiService.getPlantas()
                Log.d("PlantasScreen", "Se obtuvieron ${plantas.size} plantas")
                isLoading = false
            } catch (e: Exception) {
                Log.e("PlantasScreen", "Error al obtener plantas: ${e.localizedMessage}", e)
                errorMessage = "Error al obtener plantas: ${e.localizedMessage}"
                isLoading = false
            }
        }
    }

    when {
        isLoading -> {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        errorMessage != null -> {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(errorMessage ?: "Error desconocido")
            }
        }

        else -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(plantas) { planta ->
                    PlantaCard(planta)
                }
            }
        }
    }
}

@Composable
fun PlantaCard(planta: Planta) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(planta.imagen)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = planta.nombre,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(text = planta.nombre, style = MaterialTheme.typography.titleMedium)
                Text(text = "ID: ${planta.id}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
