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

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        register("libs") {
            version("kotlin", "1.7.20")
            version("coroutines", "1.6.4")
            version("watchface", "1.1.1")
            version("room", "2.5.0-alpha03")
            version("ktor", "2.5.0-alpha03")
            version("ktorfit", "2.5.0-alpha03")
            library("coil", "io.coil-kt:coil:2.2.1")
            library("material", "com.google.android.material:material:1.8.0-alpha01")
            library("koin", "io.insert-koin:koin-android:3.2.2")
            library("timber", "com.jakewharton.timber:timber:5.0.1")
            library("desugar", "com.android.tools:desugar_jdk_libs:2.0.0")
            library(
                "coroutines",
                "org.jetbrains.kotlinx",
                "kotlinx-coroutines-android"
            )
                .versionRef("coroutines")
            library(
                "coroutines-test",
                "org.jetbrains.kotlinx",
                "kotlinx-coroutines-test"
            ).versionRef("coroutines")
        }

        register("androidx") {
            library("wear", "androidx.wear:wear:1.3.0-alpha03")
            version("lifecycle", "2.5.1")
            library("core", "androidx.core:core-ktx:1.9.0")
            library("activity", "androidx.activity:activity-ktx:1.6.0")
            library("constraint", "androidx.constraintlayout:constraintlayout:2.1.4")
            library("datastore", "androidx.datastore:datastore-preferences:1.0.0")
            library("lifecycle", "androidx.lifecycle", "lifecycle-runtime-ktx").versionRef(
                "lifecycle"
            )
            library("viewmodel", "androidx.lifecycle", "lifecycle-viewmodel-ktx").versionRef(
                "lifecycle"
            )
        }

        register("firebase") {
            library("bom", "com.google.firebase:firebase-bom:30.5.0")
            library(
                "crashlytics",
                "com.google.firebase",
                "firebase-crashlytics-ktx"
            ).withoutVersion()
            library(
                "analytics",
                "com.google.firebase",
                "firebase-analytics-ktx"
            ).withoutVersion()
        }
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        id("io.gitlab.arturbosch.detekt").version("1.21.0")
        id("com.google.devtools.ksp") version "1.7.20-1.0.6"
    }
}

rootProject.name = "WearMMR"

include(":app", ":data", ":domain")
