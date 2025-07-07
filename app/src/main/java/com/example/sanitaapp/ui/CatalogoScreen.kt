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
import androidx.compose.material3.Button // Use material3 Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults // Importar OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.material3.ButtonDefaults
private const val CATEGORIA_CICATRIZANTES = 1
private const val CATEGORIA_INMUNOLOGICO = 2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen() {
    val coroutineScope = rememberCoroutineScope()
    var plantas by remember { mutableStateOf<List<Planta>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var selectedCategory by remember { mutableStateOf<Int?>(CATEGORIA_CICATRIZANTES) } // Cicatrizantes por defecto
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                plantas = ApiClient.apiService.getPlantas()
                println("CATEGORÍAS: " + plantas.map { it.nombre + ": " + it.categoria })

                isLoading = false
            } catch (e: Exception) {
                errorMessage = "Error loading plants: ${e.localizedMessage}"
                isLoading = false
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.fondocatalogo),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(modifier = Modifier.fillMaxSize()) {
            // Top AppBar (Customized for title positioning)
            TopAppBar(
                title = {
                    Text(
                        "Productos",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White,
                        modifier = Modifier.padding(start = 16.dp) // Ajustar padding del título si es necesario
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )

            // Search Bar
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Buscar plantas...", color = Color.White) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(8.dp)), // Bordes redondeados
                colors = OutlinedTextFieldDefaults.colors( // Usar OutlinedTextFieldDefaults.colors
                    focusedContainerColor = Color.Transparent, // Color de fondo cuando está enfocado
                    unfocusedContainerColor = Color.Transparent, // Color de fondo cuando no está enfocado
                    focusedBorderColor = Color.White, // Color del borde cuando está enfocado
                    unfocusedBorderColor = Color.Gray, // Color del borde cuando no está enfocado
                    cursorColor = Color.White, // Color del cursor
                    focusedTextColor = Color.White, // Color del texto cuando está enfocado
                    unfocusedTextColor = Color.White, // Color del texto cuando no está enfocado
                    // Si necesitas cambiar el color del label o el ícono:
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.Gray,
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.Gray
                )
            )

            // Filter Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        selectedCategory = if (selectedCategory == CATEGORIA_CICATRIZANTES) null else CATEGORIA_CICATRIZANTES
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedCategory == CATEGORIA_CICATRIZANTES) Color(0xFF4CAF50) else Color.Gray
                    )
                ) {
                    Text("Cicatrizantes", color = Color.White)
                }

                Button(
                    onClick = {
                        selectedCategory = if (selectedCategory == CATEGORIA_INMUNOLOGICO) null else CATEGORIA_INMUNOLOGICO
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedCategory == CATEGORIA_INMUNOLOGICO) Color(0xFF4CAF50) else Color.Gray
                    )
                ) {
                    Text("Sistema Inmunológico", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when {
                isLoading -> {
                    // ... (código de isLoading)
                }
                errorMessage != null -> {
                    // ... (código de errorMessage)
                }
                else -> {
                    // Mover la lógica de filtrado aquí para que se recalcule con los cambios
                    val filteredPlantas = plantas.filter { planta ->
                        val matchesCategory = selectedCategory?.let { planta.categoria == it } ?: true
                        val matchesSearch = planta.nombre.contains(searchText, ignoreCase = true)
                        matchesCategory && matchesSearch
                    }

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
            // Plant Image
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
                        .clip(RoundedCornerShape(4.dp)), // Bordes redondeados para la imagen
                    contentScale = ContentScale.Crop
                )
            } else {
                // Placeholder if image not found
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color.Gray)
                        .clip(RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Image not found", fontSize = 10.sp, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = planta.nombre, style = MaterialTheme.typography.titleMedium)
                // Mostrar el precio real
                Text(text = "S/. ${planta.precio}", style = MaterialTheme.typography.bodySmall)
            }

            // Shopping Cart Icon
            Icon(
                painter = painterResource(id = R.drawable.carrito),
                contentDescription = "Add to Cart",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { /* TODO: Implement add to cart */ } // Hacer el ícono clickable
            )
        }
    }
}