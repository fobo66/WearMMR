import org.jetbrains.kotlin.config.KotlinCompilerVersion
import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("io.gitlab.arturbosch.detekt")
}

/*
 * Copyright 2018. Andrey Mukamolov
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

val wearableVersion = "2.9.0"
val wearableWatchfaceVersion = "1.1.0-rc01"
val retrofitVersion = "2.9.0"
val roomVersion = "2.5.0-alpha02"
val lifecycleVersion = "2.5.0-rc01"
val koinVersion = "3.2.0"
val glideVersion = "4.13.2"
val moshiVersion = "1.13.0"

android {

    compileSdk = 32
    defaultConfig {
        applicationId = "io.github.fobo66.wearmmr"
        minSdk = 26
        targetSdk = 32
        versionCode = 5
        versionName = "2.0"
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
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

    kotlinOptions {
        jvmTarget = "11"
    }
    namespace = "io.github.fobo66.wearmmr"

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
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.activity:activity-ktx:1.5.0-rc01")
    implementation("androidx.fragment:fragment-ktx:1.5.0-rc01")
    implementation("androidx.preference:preference-ktx:1.2.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

    compileOnly("com.google.android.wearable:wearable:$wearableVersion")
    implementation("androidx.wear:wear:1.3.0-alpha02")
    implementation("androidx.wear.watchface:watchface:$wearableWatchfaceVersion")
    implementation("androidx.wear.watchface:watchface-complications-rendering:$wearableWatchfaceVersion")
    implementation("androidx.wear.watchface:watchface-complications-data-source:$wearableWatchfaceVersion")
    implementation("androidx.wear.watchface:watchface-complications-data-source-ktx:$wearableWatchfaceVersion")
    implementation("androidx.wear.watchface:watchface-editor:$wearableWatchfaceVersion")

    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    implementation("com.squareup.moshi:moshi:$moshiVersion")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.2")

    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    implementation("io.insert-koin:koin-android:$koinVersion")

    implementation("com.github.bumptech.glide:glide:$glideVersion")
    kapt("com.github.bumptech.glide:compiler:$glideVersion")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(platform("com.google.firebase:firebase-bom:30.1.0"))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("net.danlew:android.joda:2.10.14")
    implementation("com.jakewharton.timber:timber:5.0.1")
}
