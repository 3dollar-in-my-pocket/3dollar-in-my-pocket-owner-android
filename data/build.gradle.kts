import java.io.FileInputStream
import java.util.Properties

plugins {
    id("threedollars.android.library")
    id("kotlinx-serialization")
}

android {
    val localProperties = Properties()
    localProperties.load(FileInputStream(rootProject.file("local.properties")))

    buildFeatures {
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
    namespace = "app.threedollars.data"
}

dependencies {
    implementation(projects.common)
    implementation(projects.domain)

    implementation(libs.okhttp3)
    implementation(libs.okhttp3.logging)
    implementation(libs.retrofit)
    implementation(libs.moshi)
    implementation(libs.moshi.converter)
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.codegen)
    implementation(libs.datastore)
    implementation(libs.datastore.preferences)
    implementation(libs.paging)
    implementation(libs.gson)
    implementation(libs.gson.converter)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlin.serialization)

}