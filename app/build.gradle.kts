/*
 *    Copyright 2025 Andrey Mukamolov
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.compose")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "io.github.fobo66.wearmmr"

    compileSdk = 35
    defaultConfig {
        applicationId = "io.github.fobo66.wearmmr"
        minSdk = 26
        targetSdk = 35
        versionCode = 10
        versionName = "2.2"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        register("releaseSign") {
            keyAlias = loadSecret(rootProject, KEY_ALIAS)
            keyPassword = loadSecret(rootProject, KEY_PASSWORD)
            storeFile = rootProject.file(loadSecret(rootProject, STORE_FILE))
            storePassword = loadSecret(rootProject, STORE_PASSWORD)
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs["releaseSign"]
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = false
        viewBinding = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    lint {
        disable += "DialogFragmentCallbacksDetector"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

tasks.withType<Detekt> {
    jvmTarget = "17"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(":domain"))

    implementation(androidx.core)
    implementation(androidx.activity)
    implementation(androidx.lifecycle)
    implementation(androidx.viewmodel)
    implementation(androidx.appstartup)
    implementation(androidx.constraint)
    implementation(androidx.wear)
    implementation(androidx.wear.phoneinteraction)
    implementation(libs.androidx.material)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.foundation)
    implementation(libs.wear.compose.material)
    implementation(libs.wear.compose.material3)
    implementation(libs.wear.compose.foundation)
    implementation(libs.compose.material.icons)
    implementation(libs.wear.compose.navigation)
    implementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.preview)
    debugImplementation(libs.wear.compose.preview)

    implementation(watchface.core)
    implementation(watchface.complication)
    implementation(watchface.complication.datasource)
    implementation(watchface.complication.datasource.ktx)
    implementation(watchface.editor)

    implementation(libs.kotlinx.coroutines.android)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.viewmodel)

    implementation(libs.coil)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.timber)
}
