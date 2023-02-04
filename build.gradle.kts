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

 plugins {
     id("com.android.application") version "8.1.0-alpha02" apply false
     id("com.android.library") version "8.1.0-alpha02" apply false
     kotlin("android") version "1.8.10" apply false
     kotlin("plugin.serialization") version "1.8.10" apply false
     id("io.gitlab.arturbosch.detekt") version "1.22.0" apply false
     id("com.google.devtools.ksp") version "1.8.0-1.0.8" apply false
     id("com.google.gms.google-services") version "4.3.15" apply false
     id("com.google.firebase.crashlytics") version "2.9.2" apply false
     id("de.jensklingenberg.ktorfit") version "1.0.0" apply false
 }

tasks {
    register<Delete>("clean") {
        delete(rootProject.buildDir)
    }
}
