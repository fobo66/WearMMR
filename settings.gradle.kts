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
        register("buildscriptPlugins") {
            library("android", "com.android.tools.build:gradle:8.0.0-alpha05")
            library("crashlytics", "com.google.firebase:firebase-crashlytics-gradle:2.9.2")
            library("googleServices", "com.google.gms:google-services:4.3.14")
        }

        register("libs") {
            version("kotlin", "1.7.20")
            version("coroutines", "1.6.4")
            library("coil", "io.coil-kt:coil:2.2.2")
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
            library("appstartup", "androidx.startup:startup-runtime:1.1.1")
            library("constraint", "androidx.constraintlayout:constraintlayout:2.1.4")
            library("datastore", "androidx.datastore:datastore-preferences:1.0.0")
            library("lifecycle", "androidx.lifecycle", "lifecycle-runtime-ktx").versionRef(
                "lifecycle"
            )
            library("viewmodel", "androidx.lifecycle", "lifecycle-viewmodel-ktx").versionRef(
                "lifecycle"
            )
            library("uitest.core", "androidx.test:core-ktx:1.4.0")
            library("uitest.junit", "androidx.test.ext:junit-ktx:1.1.3")
            library("uitest.runner", "androidx.test:runner:1.4.0")
        }

        register("watchface") {
            version("watchface", "1.1.1")
            library("core", "androidx.wear.watchface", "watchface").versionRef("watchface")
            library("editor", "androidx.wear.watchface", "watchface-editor").versionRef("watchface")
            library(
                "complication",
                "androidx.wear.watchface",
                "watchface-complications-rendering"
            ).versionRef("watchface")
            library(
                "complication-datasource",
                "androidx.wear.watchface",
                "watchface-complications-data-source"
            ).versionRef("watchface")
            library(
                "complication-datasource-ktx",
                "androidx.wear.watchface",
                "watchface-complications-data-source-ktx"
            ).versionRef("watchface")
        }

        register("room") {
            version("room", "2.5.0-beta01")
            library("runtime", "androidx.room", "room-runtime").versionRef("room")
            library("ktx", "androidx.room", "room-ktx").versionRef("room")
            library("compiler", "androidx.room", "room-compiler").versionRef("room")
        }

        register("ktor") {
            version("ktor", "2.1.2")
            library("client", "io.ktor", "ktor-client-okhttp").versionRef("ktor")
            library("client-mock", "io.ktor", "ktor-client-mock").versionRef("ktor")
            library("content", "io.ktor", "ktor-client-content-negotiation").versionRef("ktor")
            library("json", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")
        }

        register("ktorfit") {
            version("ktorfit", "1.0.0-beta15")
            library("library", "de.jensklingenberg.ktorfit", "ktorfit-lib").versionRef("ktorfit")
            library("processor", "de.jensklingenberg.ktorfit", "ktorfit-ksp").versionRef("ktorfit")
        }

        register("firebase") {
            library("bom", "com.google.firebase:firebase-bom:31.0.0")
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
        id("io.gitlab.arturbosch.detekt") version "1.21.0"
        id("com.google.devtools.ksp") version "1.7.20-1.0.7"
    }
}

rootProject.name = "WearMMR"

include(":app", ":data", ":domain")
