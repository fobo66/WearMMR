plugins {
    id("com.android.library")
    kotlin("android")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "io.github.fobo66.wearmmr.data"
    compileSdk = 32

    defaultConfig {
        minSdk = 26
        targetSdk = 32

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
}

dependencies {
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("io.insert-koin:koin-android:3.2.0")
}
