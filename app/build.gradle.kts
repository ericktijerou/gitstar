plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.kotlinAndroid)
    id(Plugins.daggerHilt)
    id(Plugins.ktLint)
    id(Plugins.apollo).version("2.5.3")
    kotlin(Plugins.kotlinKapt)
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        applicationId = "com.ericktijerou.gitstar"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        javaCompileOptions {
            annotationProcessorOptions {
                arguments.plusAssign(
                    hashMapOf(
                        "room.schemaLocation" to "$projectDir/schemas",
                        "room.incremental" to "true",
                        "room.expandProjection" to "true"
                    )
                )
            }
        }
        val githubOauthKey = Globals.buildProperties["githubOauthKey"]
        buildConfigField("String", "GITHUB_OAUTH_TOKEN", "\"$githubOauthKey\"")
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            isTestCoverageEnabled = true

            buildConfigField(
                "String",
                "API_URL",
                "\"https://api.github.com/graphql\""
            )
        }

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            isZipAlignEnabled = true
            proguardFile(getDefaultProguardFile("proguard-android.txt"))
            proguardFile(file("proguard-rules.pro"))

            buildConfigField(
                "String",
                "API_URL",
                "\"https://api.github.com/graphql\""
            )
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        useIR = true
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
        kotlinCompilerVersion = Versions.kotlin
    }
}

ktlint {
    android.set(true)
    outputColorName.set("RED")
}

apollo {
    generateKotlinModels.set(true)
}

dependencies {
    implementation(Libs.Android.coreKtx)
    implementation(Libs.Android.appcompat)
    implementation(Libs.Android.materialDesign)
    implementation(Libs.Compose.ui)
    implementation(Libs.Compose.material)
    implementation(Libs.Compose.tooling)
    implementation(Libs.Lifecycle.viewModel)
    implementation(Libs.Lifecycle.liveData)
    implementation(Libs.Dagger.hiltAndroid)
    implementation(Libs.Dagger.hiltViewModel)
    kapt(Libs.Dagger.daggerCompiler)
    kapt(Libs.Dagger.hiltCompiler)
}