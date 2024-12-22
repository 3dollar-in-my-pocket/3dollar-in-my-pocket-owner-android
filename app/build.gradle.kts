import java.io.FileInputStream
import java.util.Properties

plugins {
    id("threedollars.android.application")
    id("com.google.gms.google-services")
    id("kotlinx-serialization")
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
    implementation(projects.common)
    implementation(projects.data)
    implementation(projects.domain)
    implementation(projects.feature.home)
    implementation(projects.feature.setting)
    implementation(projects.navigation)

    implementation(libs.firebase.messaging.ktx)
    implementation(libs.kakao.login)
    implementation(libs.naver.map.compose)
    implementation(libs.google.location)
    implementation(libs.compose.permissions)
    implementation(libs.lottie)
    implementation(libs.compose.coil)
    implementation(libs.paging.compose)
    implementation(libs.dialog.compose)
    implementation(libs.dialog.time.compose)

    implementation(libs.moshi.kotlin)
    implementation(libs.kotlinx.serialization.json)
}