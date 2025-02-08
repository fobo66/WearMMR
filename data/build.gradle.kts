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
    id("kotlinx-serialization")
    id("kotlin-parcelize")
    id("io.gitlab.arturbosch.detekt")
    id("com.google.devtools.ksp")
    id("de.jensklingenberg.ktorfit")
    alias(libs.plugins.room)
}

android {
    namespace = "io.github.fobo66.wearmmr.data"
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

    libraryVariants.all {
        val variantName = this.name
        kotlin.sourceSets {
            getByName(variantName) {
                kotlin.srcDir("build/generated/ksp/$variantName/kotlin")
            }
        }
    }

    lint {
        disable += "DialogFragmentCallbacksDetector"
    }

    testOptions {
        animationsDisabled = true
    }
}

room {
    generateKotlin = true
    schemaDirectory(layout.projectDirectory.dir("schemas"))
}

tasks.withType<Detekt> {
    jvmTarget = "17"
}

dependencies {
    implementation(androidx.datastore)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(ktor.client)
    implementation(ktor.content)
    implementation(ktor.json)
    implementation(apiclient.library)
    implementation(libs.timber)
    androidTestImplementation(androidx.uitest.core)
    androidTestImplementation(androidx.uitest.junit)
    androidTestImplementation(androidx.uitest.runner)
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
    testImplementation(ktor.client.mock)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.kotlinx.coroutines.test)
}
