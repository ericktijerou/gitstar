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
        classpath(ClassPaths.ktLintPlugin)
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

Globals.buildProperties.load(FileInputStream("apikey.properties"))