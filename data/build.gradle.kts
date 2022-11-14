/*
 *    Copyright 2022 Andrey Mukamolov
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

import com.android.build.api.dsl.ManagedVirtualDevice
import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlinx-serialization")
    id("kotlin-parcelize")
    id("io.gitlab.arturbosch.detekt")
    id("com.google.devtools.ksp")
}

android {
    namespace = "io.github.fobo66.wearmmr.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = false
    }

    libraryVariants.all {
        val variantName = this.name
        kotlin.sourceSets {
            getByName(variantName) {
                kotlin.srcDir("build/generated/ksp/$variantName/kotlin")
            }
        }
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
        arg("room.incremental", "true")
        arg("room.expandProjection", "true")
    }

    lint {
        disable += "DialogFragmentCallbacksDetector"
    }

    testOptions {
        animationsDisabled = true
        managedDevices {
            devices.register<ManagedVirtualDevice>("pixel") {
                device = "Pixel 2"
                apiLevel = 30
                systemImageSource = "aosp-atd"
                require64Bit = false
            }
        }
    }
}

tasks.withType<Detekt> {
    jvmTarget = "11"
}

dependencies {
    implementation(androidx.datastore)
    implementation(koin.core)
    implementation(libs.coroutines)
    implementation(room.runtime)
    implementation(room.ktx)
    ksp(room.compiler)
    implementation(ktor.client)
    implementation(ktor.content)
    implementation(ktor.json)
    implementation(ktorfit.library)
    ksp(ktorfit.processor)
    coreLibraryDesugaring(libs.desugar)
    implementation(libs.timber)
    androidTestImplementation(androidx.uitest.core)
    androidTestImplementation(androidx.uitest.junit)
    androidTestImplementation(androidx.uitest.runner)
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
    testImplementation(ktor.client.mock)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.coroutines.test)
}
