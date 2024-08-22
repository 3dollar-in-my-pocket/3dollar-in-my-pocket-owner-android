import Dependencies.common
import java.io.FileInputStream
import java.util.Properties

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
    val localProperties = Properties()
    localProperties.load(FileInputStream(rootProject.file("local.properties")))

    compileSdk = 34
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
        targetSdk = 34
        versionCode = 7
        versionName = "1.1.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        compose = true
        buildConfig = true
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
            buildConfigField("String", "KAKAO_KEY","${localProperties["kakao_key_release"]}")
            buildConfigField("String", "BASE_URL", "${localProperties["base_url_release"]}")

            manifestPlaceholders["kakao_key"] = (localProperties["kakao_key_release"] as String).replace("\"","")
            manifestPlaceholders["naver_map_client_id"] = localProperties["naver_map_client_id"] as String
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
            buildConfigField("String", "KAKAO_KEY","${localProperties["kakao_key_dev"]}")
            buildConfigField("String", "BASE_URL", "${localProperties["base_url_dev"]}")
            manifestPlaceholders["kakao_key"] = (localProperties["kakao_key_dev"] as String).replace("\"","")
            manifestPlaceholders["naver_map_client_id"] = localProperties["naver_map_client_id"] as String
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    namespace = "app.threedollars.manager"
}

dependencies {
    common()
    implementation(project(":common"))
    implementation(project(":data"))
    implementation(project(":domain"))
}