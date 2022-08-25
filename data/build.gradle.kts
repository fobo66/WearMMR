import com.android.build.api.dsl.ManagedVirtualDevice

plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-parcelize")
    id("io.gitlab.arturbosch.detekt")
    id("com.google.devtools.ksp") version "1.7.10-1.0.6"
}

android {
    namespace = "io.github.fobo66.wearmmr.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }

    lint {
        disable += "DialogFragmentCallbacksDetector"
    }

    testOptions {
        animationsDisabled = true
        managedDevices {
            devices.register<ManagedVirtualDevice>("pixel") {
                device = "Pixel 2"
                apiLevel = 30
                systemImageSource = "aosp-atd"
                require64Bit = false
            }
        }
    }
}

dependencies {
    val roomVersion = "2.5.0-alpha02"
    val coroutinesVersion = "1.6.4"

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.preference:preference-ktx:1.2.0")
    implementation("io.insert-koin:koin-android:3.2.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    androidTestImplementation("androidx.test:core-ktx:1.4.0")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.3")
    androidTestImplementation("androidx.test:runner:1.4.0")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
}
