import Dependencies.common
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.appdistribution")
}

android {

    compileSdk = 33
    signingConfigs {
        create("release") {
            storeFile = file("ThreeDollarsManager.jks")
            storePassword = "ThreeDollarsManager"
            keyAlias = "ThreeDollarsManager"
            keyPassword = "ThreeDollarsManager"
        }
    }

    defaultConfig {
        applicationId = "app.threedollars.manager"
        minSdk = 23
        targetSdk = 33
        versionCode = 3
        versionName = "1.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        compose = true
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
            manifestPlaceholders["appName3dollar"] = "@string/app_name"
            applicationIdSuffix = ""
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "KAKAO_KEY", gradleLocalProperties(rootDir)["kakao_key_release"] as? String ?: "")
            buildConfigField("String", "BASE_URL", gradleLocalProperties(rootDir)["base_url_release"] as? String ?: "")
            manifestPlaceholders["kakao_key"] = (gradleLocalProperties(rootDir)["kakao_key_release"] as String).replace("\"","")
            manifestPlaceholders["naver_map_client_id"] = gradleLocalProperties(rootDir)["naver_map_client_id"] as String
        }
        getByName("debug") {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            manifestPlaceholders["appName3dollar"] = "@string/app_name_debug"
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            firebaseAppDistribution {
                releaseNotesFile = "./release_note.txt"
                testers = "android"
            }
            buildConfigField("String", "KAKAO_KEY", gradleLocalProperties(rootDir)["kakao_key_dev"] as? String ?: "")
            buildConfigField("String", "BASE_URL", gradleLocalProperties(rootDir)["base_url_dev"] as? String ?: "")
            manifestPlaceholders["kakao_key"] = (gradleLocalProperties(rootDir)["kakao_key_dev"] as String).replace("\"","")
            manifestPlaceholders["naver_map_client_id"] = gradleLocalProperties(rootDir)["naver_map_client_id"] as String
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0-alpha02"
    }
    namespace = "app.threedollars.manager"
}

dependencies {
    common()
    implementation(project(":common"))
    implementation(project(":data"))
    implementation(project(":domain"))
}