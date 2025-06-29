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
            // --- INICIO CÓDIGO MEJORADO PARA CARGAR IMAGEN DESDE DRAWABLE ---

            // 1. Obtener solo el nombre del archivo sin la ruta del servidor (ej. "ayahuasca.jpeg")
            val fileNameWithExtension = planta.imagen.substringAfterLast("/")

            // 2. Limpiar el nombre para que sea un recurso válido de Android
            val resourceName = fileNameWithExtension
                .substringBeforeLast('.', missingDelimiterValue = fileNameWithExtension)
                .replace('-', '_')
                .lowercase()

            // 3. Obtener el ID del recurso drawable
            val context = LocalContext.current
            val resourceId = context.resources.getIdentifier(
                resourceName, "drawable", context.packageName
            )

            // 4. Cargar la imagen usando el ID del recurso
            if (resourceId != 0) {
                // Si el recurso se encuentra en drawable, cárgalo localmente
                Image(
                    painter = rememberAsyncImagePainter(model = resourceId), // <-- Carga local
                    contentDescription = planta.nombre,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(end = 16.dp),
                    contentScale = ContentScale.Crop
                )
                Log.d("PlantasScreen", "Imagen '$resourceName' cargada desde drawable. ID: $resourceId")
            } else {
                // Si el recurso NO se encuentra, muestra un log de error y una imagen de placeholder
                Log.e("PlantasScreen", "ERROR: Recurso no encontrado. Buscando '$resourceName' en drawable. " +
                        "Asegúrate de que el archivo '$fileNameWithExtension' existe en res/drawable " +
                        "y su nombre es '${resourceName}.*'")

                // Opcional: Muestra una imagen de placeholder para que no quede vacío
                // Asegúrate de tener un archivo llamado `placeholder_image.png` en `res/drawable`
                Image(
                    painter = rememberAsyncImagePainter(model = R.drawable.ic_launcher_foreground), // Usa un ícono por defecto
                    contentDescription = "Imagen no encontrada",
                    modifier = Modifier
                        .size(64.dp)
                        .padding(end = 16.dp),
                    contentScale = ContentScale.Crop
                )
            }
            // --- FIN CÓDIGO MEJORADO ---

            Column {
                Text(text = planta.nombre, style = MaterialTheme.typography.titleMedium)
                Text(text = "ID: ${planta.id}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
