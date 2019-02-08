// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    val kotlin_version = "1.3.11"
    repositories {
        google()
        jcenter()

        maven("https://maven.fabric.io/public")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:3.3.1")
        classpath(kotlin("gradle-plugin", version = kotlin_version))
        classpath("com.google.gms:google-services:4.2.0")
        classpath("io.fabric.tools:gradle:1.25.4")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
