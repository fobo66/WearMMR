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

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.compose")
}

android {
    namespace = "dev.fobo66.wearmmr.companion"
    compileSdk = 35

    defaultConfig {
        applicationId = "dev.fobo66.wearmmr.companion"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(androidx.core)
    implementation(androidx.activity)
    implementation(androidx.lifecycle)
    implementation(androidx.viewmodel)
    implementation(androidx.appstartup)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.preview)
    implementation(libs.compose.material)

    implementation(libs.kotlinx.coroutines.android)

    implementation(koin.core)
    implementation(koin.compose)

    implementation(libs.coil)

    implementation(platform(firebase.bom))
    implementation(firebase.crashlytics)
    implementation(firebase.analytics)
    implementation(libs.timber)

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))

    androidTestImplementation(androidx.uitest.core)
    androidTestImplementation(androidx.uitest.junit)
    androidTestImplementation(androidx.uitest.runner)
}
