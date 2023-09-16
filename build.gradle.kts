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
     alias(agp.plugins.application) apply false
     alias(agp.plugins.library) apply false
     kotlin("android") version libs.versions.kotlin apply false
     kotlin("plugin.serialization") version libs.versions.kotlin apply false
     alias(libs.plugins.detekt) apply false
     alias(libs.plugins.ksp) apply false
     alias(firebase.plugins.googleServices) apply false
     alias(firebase.plugins.crashlytics) apply false
     alias(apiclient.plugins.ktorfit) apply false
 }

tasks {
    register<Delete>("clean") {
        delete(rootProject.layout.buildDirectory)
    }
}
