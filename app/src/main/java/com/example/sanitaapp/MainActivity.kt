package com.example.sanitaapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sanitaapp.ui.CatalogoScreen
import com.example.sanitaapp.ui.ComunidadScreen
import com.example.sanitaapp.ui.DetailScreen
import com.example.sanitaapp.ui.HomeScreen
import com.example.sanitaapp.ui.components.NoticiasScreen
import com.example.sanitaapp.ui.components.NoticiaDetailScreen
import com.example.sanitaapp.ui.components.AppBottomNavigationBar
import com.example.sanitaapp.ui.components.AppTopBar
import com.example.sanitaapp.ui.theme.SanitaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SanitaAppTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { AppTopBar() },
                    bottomBar = { AppBottomNavigationBar(navController = navController) }
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "home_screen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // Pantalla principal
                        composable("home_screen") {
                            HomeScreen(navController = navController)
                        }

                        // Catálogo
                        composable("catalogo_screen") {
                            CatalogoScreen()
                        }

                        // Comunidad
                        composable("comunidad_screen") {
                            ComunidadScreen()
                        }

                        // Noticias (lista de noticias)
                        composable("noticias_screen") {
                            NoticiasScreen(navController = navController)
                        }

                        // Detalle de noticia
                        composable(
                            route = "detalle_noticia/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val noticiaId = backStackEntry.arguments?.getInt("id")
                            if (noticiaId != null) {
                                NoticiaDetailScreen(noticiaId = noticiaId)
                            } else {
                                Log.e("MainActivity", "ID de noticia no encontrado")
                                Text("Error: ID de noticia no encontrado", color = Color.Red)
                            }
                        }

                        // Detalle de planta
                        composable(
                            route = "detail_screen/{plantaId}",
                            arguments = listOf(navArgument("plantaId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val plantaId = backStackEntry.arguments?.getInt("plantaId")
                            if (plantaId != null) {
                                DetailScreen(plantaId = plantaId)
                            } else {
                                Log.e("MainActivity", "ID de planta no encontrado")
                                Text("Error: ID de planta no encontrado", color = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }
}