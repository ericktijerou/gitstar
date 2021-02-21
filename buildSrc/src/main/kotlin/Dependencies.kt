object Libs {
    object Android {
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val materialDesign = "com.google.android.material:material:${Versions.materialDesign}"
    }

    object Kotlin {
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    }

    object Dagger {
        const val daggerCompiler = "com.google.dagger:hilt-android-compiler:${Versions.daggerHilt}"
        const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.daggerHiltAndroid}"
        const val hiltViewModel =
            "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.daggerHiltAndroid}"
        const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.daggerHilt}"
    }

    object Coil {
        const val coil = "io.coil-kt:coil:${Versions.coil}"
    }

    object Lifecycle {
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    }

    object Room {
        const val compiler = "androidx.room:room-compiler:${Versions.room}"
        const val ktx = "androidx.room:room-ktx:${Versions.room}"
        const val runtime = "androidx.room:room-runtime:${Versions.room}"
    }

    object Compose {
        const val composeRunTime = "androidx.compose.runtime:runtime:${Versions.compose}"
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val composeFoundation = "androidx.compose.foundation:foundation:${Versions.compose}"
        const val composeLayout =
            "androidx.compose.foundation:foundation-layout:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val composeIcons = "androidx.compose.material:material-icons-core:${Versions.compose}"
        const val composeIconsExtended =
            "androidx.compose.material:material-icons-extended:${Versions.compose}"
        const val composeUiTest = "androidx.compose.ui:ui-test:${Versions.compose}"
        const val composeLifecycle = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
        const val composeNavigation =
            "androidx.navigation:navigation-compose:${Versions.composeNav}"
        const val composePaging = "androidx.paging:paging-compose:${Versions.composePaging}"
    }

    object Apollo {
        const val apollo = "com.apollographql.apollo:apollo-runtime:${Versions.apollo}"
        const val apolloCoroutines =
            "com.apollographql.apollo:apollo-coroutines-support:${Versions.apollo}"
    }

    object Paging {
        const val runtime = "androidx.paging:paging-runtime:${Versions.paging}"
    }

    object OkHttp {
        const val okHttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    }

    object Coroutines {
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }
}

object ClassPaths {
    const val gradlePlugin = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val daggerPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.daggerHilt}"
    const val ktLintPlugin = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktLint}"
    const val apolloPlugin = "com.apollographql.apollo:apollo-gradle-plugin:${Versions.apollo}"
}

object Plugins {
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "android"
    const val daggerHilt = "dagger.hilt.android.plugin"
    const val ktLint = "org.jlleitschuh.gradle.ktlint"
    const val apollo = "com.apollographql.apollo"
    const val kotlinKapt = "kapt"
}

object Configs {
    const val applicationId = "com.ericktijerou.gitstar"
    const val buildToolsVersion = "30.0.3"
    const val compileSdkVersion = 30
    const val minSdkVersion = 21
    const val targetSdkVersion = 30
    const val versionCode = 1
    const val versionName = "1.0.0"
}

object Versions {
    internal const val appcompat = "1.2.0"
    internal const val gradle = "7.0.0-alpha07"
    internal const val daggerHilt = "2.31-alpha"
    internal const val daggerHiltAndroid = "1.0.0-alpha02"
    internal const val coroutines = "1.4.2"
    internal const val materialDesign = "1.3.0"
    internal const val ktLint = "9.2.1"
    internal const val coreKtx = "1.3.2"
    internal const val coil = "1.1.1"
    internal const val lifecycle = "2.3.0"
    internal const val room = "2.3.0-beta01"
    internal const val okhttp = "4.9.1"
    internal const val paging = "3.0.0-alpha13"
    internal const val composeNav = "1.0.0-alpha06"
    internal const val composePaging = "1.0.0-alpha07"
    const val kotlin = "1.4.30"
    const val apollo = "2.5.3"
    const val compose = "1.0.0-alpha12"
}
