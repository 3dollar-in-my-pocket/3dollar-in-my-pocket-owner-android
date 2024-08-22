import Dependencies.common
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    val localProperties = Properties()
    localProperties.load(FileInputStream(rootProject.file("local.properties")))

    compileSdk = 34

    defaultConfig {
        minSdk = 23
        targetSdk = 34
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "KAKAO_KEY","${localProperties["kakao_key_release"]}")
            buildConfigField("String", "BASE_URL", "${localProperties["base_url_release"]}")
        }

        getByName("debug") {
            buildConfigField("String", "KAKAO_KEY","${localProperties["kakao_key_dev"]}")
            buildConfigField("String", "BASE_URL", "${localProperties["base_url_dev"]}")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    namespace = "app.threedollars.data"
}

dependencies {
    common()
    implementation(project(":common"))
    implementation(project(":domain"))
}