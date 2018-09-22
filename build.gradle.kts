// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    repositories {
        google()
        jcenter()

        maven("https://maven.fabric.io/public")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:3.1.4")
        classpath(kotlin("gradle-plugin", version = "1.2.61"))
        classpath("com.google.gms:google-services:3.2.0")
        classpath("io.fabric.tools:gradle:1.25.4")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
