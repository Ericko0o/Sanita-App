package com.example.sanitaapp.ui

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.sanitaapp.R
import com.example.sanitaapp.api.ApiClient
import com.example.sanitaapp.model.Planta
import com.example.sanitaapp.model.ResumenItem
import kotlinx.coroutines.launch

val DarkGreen = Color(0xFF132e17)
val HighlightGreen = Color(0xFFA3E635)
val BebasNeue = androidx.compose.ui.text.font.FontFamily.Default

@Composable
fun HomeScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    var resumenItems by remember { mutableStateOf<List<ResumenItem>>(emptyList()) }
    var plantas by remember { mutableStateOf<List<Planta>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                resumenItems = ApiClient.apiService.getResumenInicio()
                Log.d("HomeScreen", "Resumen items: ${resumenItems.size}")
                plantas = ApiClient.apiService.getPlantas().take(4)
                Log.d("HomeScreen", "Plantas destacadas: ${plantas.size}")
                isLoading = false
            } catch (e: Exception) {
                Log.e("HomeScreen", "Error: ${e.localizedMessage}", e)
                errorMessage = "Error al cargar el contenido. Verifica tu conexión."
                isLoading = false
            }
        }
    }

    when {
        isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        errorMessage != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(errorMessage ?: "Error desconocido", color = Color.Red)
        }
        else -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.fondo),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(800.dp),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.2f),
                                    DarkGreen.copy(alpha = 0.9f),
                                    DarkGreen
                                ),
                                startY = 0f,
                                endY = 2000f
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    Spacer(modifier = Modifier.height(100.dp))
                    HeroSection(
                        title = "Explora el Mundo\nde la Medicina Natural",
                        highlightText = "Medicina",
                        frogImageResId = R.drawable.logo_rana
                    )
                    CarouselSection(resumenItems)
                    ExtraContentSection(
                        title = "¿Por qué elegir la medicina natural?",
                        text = "La medicina natural ofrece una alternativa saludable y sostenible para tratar diversas afecciones, utilizando plantas medicinales, técnicas ancestrales y remedios tradicionales. Explora cómo puedes cuidar tu cuerpo y mente de manera más armoniosa con la naturaleza."
                    )
                    PlantsInfoSection(plantas)
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}


@Composable
fun HeroSection(title: String, highlightText: String, frogImageResId: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp, bottom = 40.dp, start = 40.dp, end = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen de la rana/logo
        Image(
            painter = painterResource(id = frogImageResId),
            contentDescription = "Logo rana",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Fit
        )

        // Título con texto resaltado
        Text(
            text = buildAnnotatedString {
                val parts = title.split(" ")
                parts.forEach { part ->
                    if (part.equals(highlightText, ignoreCase = true)) {
                        withStyle(style = SpanStyle(color = HighlightGreen)) {
                            append(part + " ")
                        }
                    } else {
                        append(part + " ")
                    }
                }
            },
            fontFamily = BebasNeue,
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            lineHeight = 48.sp,
            style = MaterialTheme.typography.displayLarge
        )
    }
}


@Composable
fun CarouselSection(items: List<ResumenItem>) {
    val infiniteTransition = rememberInfiniteTransition(label = "carousel_transition")
    val translationX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -1f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 60000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "translationX_animation"
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items(items) { item ->
            CarouselCard(item)
        }
    }
}

@Composable
fun CarouselCard(item: ResumenItem) {
    Card(
        modifier = Modifier
            .width(350.dp)
            .height(250.dp)
            .padding(end = 12.dp)
            .clickable { /* TODO: Navegar a noticia o comunidad */ },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current).data(item.imagen).build()
                ),
                contentDescription = item.titulo,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.8f)
            )
            Text(
                text = item.titulo,
                color = Color.White,
                fontSize = 28.sp,
                fontFamily = BebasNeue,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun ExtraContentSection(title: String, text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontFamily = BebasNeue,
            fontSize = 32.sp,
            fontWeight = FontWeight.Normal,
            color = HighlightGreen,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = text,
            fontSize = 18.sp,
            lineHeight = 28.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PlantsInfoSection(plantas: List<Planta>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 40.dp)
    ) {
        Text(
            text = "Información sobre Plantas",
            fontFamily = BebasNeue,
            fontSize = 32.sp,
            fontWeight = FontWeight.Normal,
            color = HighlightGreen,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            plantas.forEach { planta ->
                PlantaInfoCard(planta)
            }
        }
    }
}

@Composable
fun PlantaInfoCard(planta: Planta) {
    val context = LocalContext.current
    val fileNameWithExtension = planta.imagen.substringAfterLast("/")
    val resourceName = fileNameWithExtension
        .substringBeforeLast('.', missingDelimiterValue = fileNameWithExtension)
        .replace('-', '_')
        .lowercase()

    val resourceId = remember(resourceName) {
        context.resources.getIdentifier(resourceName, "drawable", context.packageName)
    }

    Column(
        modifier = Modifier
            .size(150.dp)
            .clickable { /* TODO: Navegar a detalle de la planta */ }
    ) {
        if (resourceId != 0) {
            Image(
                painter = painterResource(id = resourceId),
                contentDescription = planta.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text("Img not found", color = Color.White)
            }
        }
        Text(
            text = planta.nombre,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
    }
}
