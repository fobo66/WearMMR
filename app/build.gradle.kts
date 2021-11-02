import org.jetbrains.kotlin.config.KotlinCompilerVersion
import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("io.gitlab.arturbosch.detekt")
}

/*
 * Copyright 2018. Andrey Mukamolov <fobo66@protonmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 * limitations under the License.
 */
fun loadProperties(propertiesName: String): Properties {
    val propertiesFile = rootProject.file(propertiesName)
    val properties = Properties()
    properties.load(FileInputStream(propertiesFile))
    return properties
}

val wearableVersion = "2.8.1"
val retrofitVersion = "2.9.0"
val roomVersion = "1.1.1"
val rxVersion = "2.2.21"
val rxKotlinVersion = "2.4.0"
val rxAndroidVersion = "2.1.1"
val koinVersion = "3.1.2"

android {

    compileSdk = 31
    defaultConfig {
        applicationId = "io.github.fobo66.wearmmr"
        minSdk = 25
        targetSdk = 30
        versionCode = 4
        versionName = "1.1"
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        register("releaseSign") {
            val keystoreProperties = loadProperties("keystore.properties")

            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = File(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs["releaseSign"]
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }


    kapt {
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("reflect", KotlinCompilerVersion.VERSION))

    implementation("androidx.vectordrawable:vectordrawable-animated:1.1.0")
    implementation("androidx.activity:activity-ktx:1.4.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")

    implementation("com.google.android.support:wearable:$wearableVersion")
    compileOnly("com.google.android.wearable:wearable:$wearableVersion")
    implementation("androidx.wear:wear:1.2.0")

    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion")
    implementation("com.squareup.moshi:moshi:1.12.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.12.0")

    implementation("io.reactivex.rxjava2:rxjava:$rxVersion")
    implementation("io.reactivex.rxjava2:rxandroid:$rxAndroidVersion")
    implementation("io.reactivex.rxjava2:rxkotlin:$rxKotlinVersion")

    implementation("androidx.room:room-runtime:2.3.0")
    implementation("androidx.room:room-rxjava2:2.3.0")
    kapt("androidx.room:room-compiler:2.3.0")

    implementation("io.insert-koin:koin-android:$koinVersion")

    implementation("com.github.bumptech.glide:glide:4.12.0")
    kapt("com.github.bumptech.glide:compiler:4.12.0")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation("com.google.firebase:firebase-crashlytics:18.2.4")
    implementation("com.google.firebase:firebase-analytics:20.0.0")
    implementation("net.danlew:android.joda:2.10.12.2")
}
