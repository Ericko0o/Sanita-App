package com.example.sanitaapp.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import com.example.sanitaapp.api.ApiClient
import com.example.sanitaapp.model.Nindependiente
import kotlinx.coroutines.launch

@Composable
fun NoticiasScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var noticias by remember { mutableStateOf<List<Nindependiente>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Cargar noticias al iniciar
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val response = ApiClient.apiService.obtenerNoticias()
                noticias = response
                isLoading = false
            } catch (e: Exception) {
                errorMessage = "Error al cargar noticias: ${e.message}"
                isLoading = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Noticias Sanita",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF006400),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            else -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(noticias) { noticia ->
                        NoticiaCard(noticia) {
                            navController.navigate("detalle_noticia/${noticia.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NoticiaCard(noticia: Nindependiente, onClick: () -> Unit) {
    val context = LocalContext.current

    // Convertimos el nombre del archivo en un resourceId
    val fileName = noticia.imagen.substringAfterLast("/")
    val resourceName = fileName
        .substringBeforeLast('.', missingDelimiterValue = fileName)
        .replace('-', '_')
        .lowercase()

    val resourceId = remember(resourceName) {
        context.resources.getIdentifier(resourceName, "drawable", context.packageName)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (resourceId != 0) {
                Image(
                    painter = painterResource(id = resourceId),
                    contentDescription = noticia.titulo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = noticia.titulo,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = noticia.fecha,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = noticia.contenido.take(100) + "...",
                fontSize = 14.sp
            )
        }
    }
}