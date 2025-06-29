// app/build.gradle.kts

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") // Plugin de Compose
    kotlin("kapt") // Para Glide (si se mantiene) u otras anotaciones
}

android {
    namespace = "com.example.sanitaapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sanitaapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        viewBinding = true // Mantener si todavía usas ViewBinding para alguna parte
    }
}

dependencies {
    // === Dependencias de Red y Coroutines ===
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")


    // === Librerías de Imágenes ===
    implementation("io.coil-kt:coil-compose:2.6.0") // Coil para imágenes en Compose

    // Glide: Solo si lo usas con XML/Views tradicionales.
    // Si tu app es 100% Compose y solo usas Coil, puedes eliminar estas dos líneas.
    implementation("com.github.bumptech.glide:glide:4.15.1")
    kapt("com.github.bumptech.glide:compiler:4.15.1")

    // === Dependencias Básicas de AndroidX (no relacionadas con Compose UI) ===
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // Estas dependencias solo son necesarias si las usas con XML/Views tradicionales.
    // Si tu app es 100% Compose, es seguro eliminarlas.
    // implementation("androidx.recyclerview:recyclerview:1.3.1")
    // implementation("com.google.android.material:material:1.11.0") // Material Design para Views (XML)
    // implementation("androidx.constraintlayout:constraintlayout:2.1.4")


    // === JETPACK COMPOSE DEPENDENCIAS (¡Gestionadas por el BOM!) ===
    // Importamos la plataforma BOM, su versión se gestiona en libs.versions.toml
    implementation(platform(libs.androidx.compose.bom))

    // Módulos de Compose. Se refieren a las definiciones en libs.versions.toml (sin versión explícita aquí)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3) // Componentes de Material Design 3 para Compose
    implementation(libs.androidx.compose.animation) // Módulo de animaciones de Compose (ahora definido en libs.versions.toml)
    implementation(libs.androidx.lifecycle.runtime.ktx) // Para coroutines y ciclo de vida en Compose

    // Navigation Compose: Ahora también se refiere a libs.versions.toml
    implementation(libs.androidx.navigation.compose)


    // === Dependencias de Testing ===
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Para la vista previa en Android Studio
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
