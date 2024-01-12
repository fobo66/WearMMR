/*
 *    Copyright 2023 Andrey Mukamolov
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

@file:Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        register("agp") {
            version("agp", "8.2.1")
            plugin("application", "com.android.application").versionRef("agp")
            plugin("library", "com.android.library").versionRef("agp")
        }

        register("libs") {
            version("kotlin", "1.9.21")
            version("coroutines", "1.7.3")
            plugin("ksp", "com.google.devtools.ksp").version("1.9.21-1.0.16")
            plugin("detekt", "io.gitlab.arturbosch.detekt").version("1.23.4")
            library("coil", "io.coil-kt:coil-compose:2.5.0")
            library("material", "com.google.android.material:material:1.11.0")
            library("timber", "com.jakewharton.timber:timber:5.0.1")
            library("desugar", "com.android.tools:desugar_jdk_libs:2.0.4")
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

        register("koin") {
            library("core", "io.insert-koin:koin-android:3.5.3")
            library("compose", "io.insert-koin:koin-androidx-compose:3.5.3")
        }

        register("compose") {
            version("compiler", "1.5.7")
            version("regular", "1.5.4")
            version("wear", "1.2.1")
            library("foundation", "androidx.compose.foundation", "foundation").versionRef("regular")
            library("preview", "androidx.compose.ui", "ui-tooling-preview").versionRef("regular")
            library("tooling", "androidx.compose.ui", "ui-tooling").versionRef("regular")
            library(
                "foundation.wear",
                "androidx.wear.compose",
                "compose-foundation"
            ).versionRef("wear")
            library("material", "androidx.compose.material3:material3:1.1.2")
            library("material3", "androidx.wear.compose:compose-material3:1.0.0-alpha15")
            library("material.wear", "androidx.wear.compose", "compose-material").versionRef("wear")
            library("navigation", "androidx.wear.compose", "compose-navigation").versionRef("wear")
        }

        register("androidx") {
            library("wear", "androidx.wear:wear:1.3.0")
            library("wear.phoneinteraction", "androidx.wear:wear-phone-interactions:1.1.0-alpha04")
            version("lifecycle", "2.6.2")
            library("core", "androidx.core:core-ktx:1.12.0")
            library("activity", "androidx.activity:activity-compose:1.8.2")
            library("appstartup", "androidx.startup:startup-runtime:1.1.1")
            library("constraint", "androidx.constraintlayout:constraintlayout:2.1.4")
            library("datastore", "androidx.datastore:datastore-preferences:1.0.0")
            library("lifecycle", "androidx.lifecycle", "lifecycle-runtime-compose").versionRef(
                "lifecycle"
            )
            library("viewmodel", "androidx.lifecycle", "lifecycle-viewmodel-compose").versionRef(
                "lifecycle"
            )
            library("uitest.core", "androidx.test:core-ktx:1.5.0")
            library("uitest.junit", "androidx.test.ext:junit-ktx:1.1.5")
            library("uitest.runner", "androidx.test:runner:1.5.2")
        }

        register("watchface") {
            version("watchface", "1.2.0")
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
            version("room", "2.6.1")
            library("runtime", "androidx.room", "room-runtime").versionRef("room")
            library("ktx", "androidx.room", "room-ktx").versionRef("room")
            library("compiler", "androidx.room", "room-compiler").versionRef("room")
        }

        register("ktor") {
            version("ktor", "2.3.7")
            library("client", "io.ktor", "ktor-client-okhttp").versionRef("ktor")
            library("client-mock", "io.ktor", "ktor-client-mock").versionRef("ktor")
            library("content", "io.ktor", "ktor-client-content-negotiation").versionRef("ktor")
            library("json", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")
        }

        register("apiclient") {
            version("ktorfit", "1.11.1")
            plugin("ktorfit", "de.jensklingenberg.ktorfit").versionRef("ktorfit")
            library("library", "de.jensklingenberg.ktorfit", "ktorfit-lib").versionRef("ktorfit")
            library("processor", "de.jensklingenberg.ktorfit", "ktorfit-ksp").versionRef("ktorfit")
        }

        register("firebase") {
            library("bom", "com.google.firebase:firebase-bom:32.7.0")
            plugin("crashlytics", "com.google.firebase.crashlytics").version("2.9.9")
            plugin("googleServices", "com.google.gms.google-services").version("4.4.0")
            library(
                "crashlytics",
                "com.google.firebase",
                "firebase-crashlytics"
            ).withoutVersion()
            library(
                "analytics",
                "com.google.firebase",
                "firebase-analytics"
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
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
    }
}

rootProject.name = "WearMMR"

include(":app", ":data", ":domain", ":companion")
