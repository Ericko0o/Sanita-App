package com.example.sanitaapp.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.example.sanitaapp.api.ApiClient
import com.example.sanitaapp.model.Nindependiente

@Composable
fun NoticiaDetailScreen(noticiaId: Int) {
    var noticia by remember { mutableStateOf<Nindependiente?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(noticiaId) {
        try {
            val response = ApiClient.apiService.obtenerNoticiaPorId(noticiaId)
            noticia = response
        } catch (e: Exception) {
            errorMessage = "Error al cargar noticia: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.padding(32.dp))
    } else if (errorMessage != null) {
        Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
    } else {
        noticia?.let {
            val resourceId = getDrawableResourceId(it.imagen, context)

            Column(modifier = Modifier.padding(16.dp)) {
                if (resourceId != 0) {
                    Image(
                        painter = painterResource(id = resourceId),
                        contentDescription = it.titulo,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Text(
                    text = it.titulo,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = it.fecha,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = it.contenido,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp
                )
            }
        }
    }
}

fun getDrawableResourceId(filePath: String, context: Context): Int {
    val fileName = filePath.substringAfterLast("/")
    val resourceName = fileName
        .substringBeforeLast('.', missingDelimiterValue = fileName)
        .replace('-', '_')
        .lowercase()

    return context.resources.getIdentifier(resourceName, "drawable", context.packageName)
}