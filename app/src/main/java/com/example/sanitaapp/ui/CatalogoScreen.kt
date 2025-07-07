package com.example.sanitaapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sanitaapp.R
import com.example.sanitaapp.api.ApiClient
import com.example.sanitaapp.model.Planta
import kotlinx.coroutines.launch

private const val CATEGORIA_CICATRIZANTES = 1
private const val CATEGORIA_INMUNOLOGICO = 2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen() {
    val coroutineScope = rememberCoroutineScope()
    var plantas by remember { mutableStateOf<List<Planta>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var selectedCategory by remember { mutableStateOf<Int?>(null) }
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                plantas = ApiClient.apiService.getPlantas()
                println("🪴 Plantas cargadas:")
                plantas.forEach {
                    println("${it.nombre} - categoría: ${it.categoria} - precio: ${it.precio}")
                }
                isLoading = false
            } catch (e: Exception) {
                errorMessage = "Error al cargar las plantas: ${e.localizedMessage}"
                isLoading = false
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondocatalogo),
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Text(
                        "Productos",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Buscar plantas...", color = Color.White) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.White)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.Gray,
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.Gray
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { selectedCategory = CATEGORIA_CICATRIZANTES },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedCategory == CATEGORIA_CICATRIZANTES) Color(0xFF4CAF50) else Color.Gray
                    )
                ) {
                    Text("Cicatrizantes", color = Color.White)
                }

                Button(
                    onClick = { selectedCategory = CATEGORIA_INMUNOLOGICO },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedCategory == CATEGORIA_INMUNOLOGICO) Color(0xFF4CAF50) else Color.Gray
                    )
                ) {
                    Text("Inmunológicos", color = Color.White)
                }

                Button(
                    onClick = { selectedCategory = null },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedCategory == null) Color(0xFF4CAF50) else Color.Gray
                    )
                ) {
                    Text("Todos", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }

                errorMessage != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(errorMessage ?: "Error desconocido", color = Color.White)
                    }
                }

                else -> {
                    val filteredPlantas = plantas.filter { planta ->
                        val matchCategory = selectedCategory?.let { it == planta.categoria } ?: true
                        val matchSearch = planta.nombre.contains(searchText, ignoreCase = true)
                        matchCategory && matchSearch
                    }

                    if (filteredPlantas.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("No se encontraron plantas.", color = Color.White)
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp)
                        ) {
                            items(filteredPlantas) { planta ->
                                PlantaItem(planta)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlantaItem(planta: Planta) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val context = LocalContext.current
            val resourceName = planta.imagen
                .substringAfterLast("/")
                .substringBeforeLast(".")
                .replace("-", "_")
                .lowercase()

            val resourceId = context.resources.getIdentifier(
                resourceName, "drawable", context.packageName
            )

            if (resourceId != 0) {
                Image(
                    painter = painterResource(id = resourceId),
                    contentDescription = planta.nombre,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color.Gray)
                        .clip(RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Imagen no encontrada", fontSize = 10.sp, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = planta.nombre, style = MaterialTheme.typography.titleMedium, color = Color.White)
                Text(text = "S/. ${planta.precio}", style = MaterialTheme.typography.bodySmall, color = Color.White)
            }

            Icon(
                painter = painterResource(id = R.drawable.carrito),
                contentDescription = "Agregar al carrito",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { /* TODO: Agregar funcionalidad */ }
            )
        }
    }
}