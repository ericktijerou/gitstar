import java.io.FileInputStream

buildscript {
    repositories {
        google()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(ClassPaths.gradlePlugin)
        classpath(ClassPaths.kotlinPlugin)
        classpath(ClassPaths.daggerPlugin)
        classpath(ClassPaths.apolloPlugin)
        classpath(ClassPaths.spotlessPlugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

Globals.buildProperties.load(FileInputStream("apikey.properties"))