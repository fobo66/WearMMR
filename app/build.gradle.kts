import org.jetbrains.kotlin.config.KotlinCompilerVersion

import java.util.*
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("io.fabric")
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

val keystoreProperties = loadProperties("keystore.properties")

val butterknifeVersion = "8.8.1"
val ankoVersion = "0.10.3"
val wearableVersion = "2.3.0"
val supportLibsVersion = "27.1.1"
val retrofitVersion = "2.3.0"
val roomVersion = "1.1.1"
val rxVersion = "2.1.7"
val rxKotlinVersion = "2.2.0"
val rxAndroidVersion = "2.0.1"
val koinVersion = "0.9.1"

android {

    compileSdkVersion(28)
    defaultConfig {
        applicationId = "io.github.fobo66.wearmmr"
        minSdkVersion(24)
        targetSdkVersion(28)
        versionCode = 2
        versionName = "1.0.1"
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        register("releaseSign") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = File(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs["releaseSign"]
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    kapt {
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))
    implementation(kotlin("reflect", KotlinCompilerVersion.VERSION))

    implementation("org.jetbrains.anko:anko-commons:$ankoVersion")
    implementation("org.jetbrains.anko:anko-sdk25:$ankoVersion")
    implementation("org.jetbrains.anko:anko-appcompat-v7:$ankoVersion")

    implementation("com.google.android.support:wearable:$wearableVersion")
    implementation("com.android.support:percent:$supportLibsVersion")
    implementation("com.android.support:animated-vector-drawable:$supportLibsVersion")
    implementation("com.android.support:support-v4:$supportLibsVersion")
    implementation("com.android.support:appcompat-v7:$supportLibsVersion")
    implementation("com.android.support:recyclerview-v7:$supportLibsVersion")
    implementation("com.android.support:wear:$supportLibsVersion")
    compileOnly("com.google.android.wearable:wearable:$wearableVersion")

    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-jackson:$retrofitVersion")
    implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.0")

    implementation("io.reactivex.rxjava2:rxjava:$rxVersion")
    implementation("io.reactivex.rxjava2:rxandroid:$rxAndroidVersion")
    implementation("io.reactivex.rxjava2:rxkotlin:$rxKotlinVersion")

    implementation("com.jakewharton:butterknife:$butterknifeVersion")
    kapt("com.jakewharton:butterknife-compiler:$butterknifeVersion")

    implementation("android.arch.persistence.room:runtime:$roomVersion")
    implementation("android.arch.persistence.room:rxjava2:$roomVersion")
    kapt("android.arch.persistence.room:compiler:$roomVersion")

    implementation("org.koin:koin-android:$koinVersion")

    implementation("com.github.bumptech.glide:glide:4.7.1")
    kapt("com.github.bumptech.glide:compiler:4.7.1")

    implementation("com.android.support.constraint:constraint-layout:1.1.3")
    implementation("com.crashlytics.sdk.android:crashlytics:2.9.5@aar") {
        isTransitive = true
    }
    implementation("com.google.firebase:firebase-core:16.0.3")
    implementation("net.danlew:android.joda:2.9.9.4")
}

apply(plugin = "com.google.gms.google-services")
