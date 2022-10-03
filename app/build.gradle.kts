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

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("io.gitlab.arturbosch.detekt")
}

val wearableWatchfaceVersion = "1.1.1"

android {
    namespace = "io.github.fobo66.wearmmr"

    compileSdk = 33
    defaultConfig {
        applicationId = "io.github.fobo66.wearmmr"
        minSdk = 26
        targetSdk = 33
        versionCode = 8
        versionName = "2.1"
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
    lint {
        disable += "DialogFragmentCallbacksDetector"
    }
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt> {
    this.jvmTarget = "11"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(":domain"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.activity)
    implementation(libs.material)
    implementation(libs.lifecycle)
    implementation(libs.viewmodel)

    implementation(libs.wear)
    implementation("androidx.wear.watchface:watchface:$wearableWatchfaceVersion")
    implementation(
        "androidx.wear.watchface:watchface-complications-rendering:$wearableWatchfaceVersion"
    )
    implementation(
        "androidx.wear.watchface:watchface-complications-data-source:$wearableWatchfaceVersion"
    )
    implementation(
        "androidx.wear.watchface:watchface-complications-data-source-ktx:$wearableWatchfaceVersion"
    )
    implementation("androidx.wear.watchface:watchface-editor:$wearableWatchfaceVersion")

    implementation(libs.coroutines)

    implementation(libs.koin)

    implementation(libs.coil)

    coreLibraryDesugaring(libs.desugar)

    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(platform(libs.firebase))
    implementation(libs.crashlytics)
    implementation(libs.analytics)
    implementation(libs.timber)
}
