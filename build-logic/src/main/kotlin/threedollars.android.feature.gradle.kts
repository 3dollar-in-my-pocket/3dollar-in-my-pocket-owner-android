import app.threedollars.manager.configureHiltAndroid
import app.threedollars.manager.libs

plugins {
    id("threedollars.android.library")
    id("threedollars.android.compose")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

configureHiltAndroid()

dependencies {

    val libs = project.extensions.libs
    implementation(libs.findLibrary("hilt.navigation.compose").get())
    implementation(libs.findLibrary("androidx.navigation.compose").get())
    implementation(libs.findLibrary("kotlinx.collections.immutable").get())
    implementation(libs.findLibrary("lifecycle.runtime.ktx").get())

    implementation(libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
    implementation(libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
}
