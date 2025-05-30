@file:Suppress("OPT_IN_IS_NOT_ENABLED")

plugins {
    kotlin("multiplatform")
    kotlin("plugin.compose")
    id("com.android.library")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization")
}

version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(21)

    androidTarget()

    jvm("desktop")

    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)

            api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
            api("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")

            api("io.ktor:ktor-client-core:3.1.0")
            api("io.ktor:ktor-client-logging:3.1.0")
            api("io.ktor:ktor-client-content-negotiation:3.1.0")
            api("io.ktor:ktor-serialization-kotlinx-json:3.1.0")
            api("org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.3")
            api("io.ktor:ktor-client-okhttp:3.1.0")

            api(project(":compose-screen:compose-screen"))
        }
        val androidMain by getting {
            kotlin.srcDirs("src/jvmMain/kotlin")
        }
        androidMain.dependencies {
            api("androidx.activity:activity-compose:1.10.1")
            api("androidx.appcompat:appcompat:1.7.0")
            api("androidx.core:core-ktx:1.16.0")

            api("androidx.compose.ui:ui-geometry-android:1.7.8")
            api("androidx.compose.ui:ui-graphics-android:1.7.8")

            api("io.modelcontextprotocol:kotlin-sdk:0.4.0")
            api("com.anthropic:anthropic-java:1.1.0")
            api("com.google.genai:google-genai:0.3.0")

            api("io.coil-kt.coil3:coil-compose:3.1.0")
            api("io.coil-kt.coil3:coil-network-okhttp:3.1.0")
        }
        val desktopMain by getting {
            kotlin.srcDirs("src/jvmMain/kotlin")
        }
        desktopMain.dependencies {
            implementation(compose.desktop.common)
            implementation(fileTree(mapOf("dir" to "${projectDir}/libs", "include" to listOf("**.jar", "**.aar"))))

            implementation("com.google.code.gson:gson:2.11.0")
            implementation("io.github.succlz123:compose-imageloader-desktop:0.0.2")

            implementation("io.modelcontextprotocol:kotlin-sdk:0.4.0")
            implementation("com.anthropic:anthropic-java:1.1.0")
            implementation("com.google.genai:google-genai:0.3.0")

//            implementation("de.dfki.mary:voice-cmu-slt-hsmm:5.2.1")
            implementation("io.github.ikfly:java-tts:1.0.2")
        }
    }
}

android {
    compileSdk = 35
    namespace = "org.succlz123.deepco.app"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlin {
        jvmToolchain(21)
    }
}

