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
    id("com.android.library")
    kotlin("android")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "io.github.fobo66.wearmmr.domain"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
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
    lint {
        disable += "DialogFragmentCallbacksDetector"
    }
}

tasks.withType<Detekt> {
    jvmTarget = "17"
}

dependencies {
    implementation(project(":data"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.timber)
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
    testImplementation(libs.kotlinx.coroutines.test)
}
