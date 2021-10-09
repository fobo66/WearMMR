// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    val kotlin_version = "1.5.31"
    repositories {
        google()
        mavenCentral()
        maven("https://maven.fabric.io/public")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.1.0-alpha13")
        classpath(kotlin("gradle-plugin", version = kotlin_version))
        classpath("com.google.gms:google-services:4.3.10")
        classpath("io.fabric.tools:gradle:1.25.4")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

