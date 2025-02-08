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

@file:Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        register("androidx") {
            library("wear", "androidx.wear:wear:1.4.0-alpha01")
            library("wear.phoneinteraction", "androidx.wear:wear-phone-interactions:1.1.0-alpha05")
            version("lifecycle", "2.8.7")
            library("core", "androidx.core:core-ktx:1.15.0")
            library("activity", "androidx.activity:activity-compose:1.9.3")
            library("appstartup", "androidx.startup:startup-runtime:1.2.0")
            library("constraint", "androidx.constraintlayout:constraintlayout:2.2.0")
            library("datastore", "androidx.datastore:datastore-preferences:1.1.2")
            library("lifecycle", "androidx.lifecycle", "lifecycle-runtime-compose").versionRef(
                "lifecycle"
            )
            library("viewmodel", "androidx.lifecycle", "lifecycle-viewmodel-compose").versionRef(
                "lifecycle"
            )
            library("uitest.core", "androidx.test:core-ktx:1.6.1")
            library("uitest.junit", "androidx.test.ext:junit-ktx:1.2.1")
            library("uitest.runner", "androidx.test:runner:1.6.2")
        }

        register("watchface") {
            version("watchface", "1.2.1")
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

        register("ktor") {
            version("ktor", "3.0.3")
            library("client", "io.ktor", "ktor-client-okhttp").versionRef("ktor")
            library("client-mock", "io.ktor", "ktor-client-mock").versionRef("ktor")
            library("content", "io.ktor", "ktor-client-content-negotiation").versionRef("ktor")
            library("json", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")
        }

        register("apiclient") {
            version("ktorfit", "2.2.0")
            plugin("ktorfit", "de.jensklingenberg.ktorfit").versionRef("ktorfit")
            library("library", "de.jensklingenberg.ktorfit", "ktorfit-lib-light").versionRef("ktorfit")
        }

        register("firebase") {
            library("bom", "com.google.firebase:firebase-bom:33.8.0")
            plugin("crashlytics", "com.google.firebase.crashlytics").version("3.0.2")
            plugin("googleServices", "com.google.gms.google-services").version("4.4.2")
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
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "WearMMR"

include(":app", ":data", ":domain", ":companion")
