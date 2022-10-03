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

tasks.withType<io.gitlab.arturbosch.detekt.Detekt> {
    this.jvmTarget = "11"
}

dependencies {
    val ktorVersion = "2.1.1"
    val ktorfitVersion = "1.0.0-beta14"

    implementation(androidx.datastore)
    implementation(libs.koin)
    implementation(libs.coroutines)
    implementation(room.runtime)
    implementation(room.ktx)
    ksp(room.compiler)
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("de.jensklingenberg.ktorfit:ktorfit-lib:$ktorfitVersion")
    ksp("de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")
    coreLibraryDesugaring(libs.desugar)
    androidTestImplementation("androidx.test:core-ktx:1.4.0")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.3")
    androidTestImplementation("androidx.test:runner:1.4.0")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
    testImplementation("io.ktor:ktor-client-mock:$ktorVersion")
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.coroutines.test)
}
