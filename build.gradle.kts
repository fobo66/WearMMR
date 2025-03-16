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

plugins {
    alias(libs.plugins.android.app) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.kotlinter) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.firebase.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.ktorfit) apply false
}

tasks {
    register<Delete>("clean") {
        delete(rootProject.layout.buildDirectory)
    }
}
