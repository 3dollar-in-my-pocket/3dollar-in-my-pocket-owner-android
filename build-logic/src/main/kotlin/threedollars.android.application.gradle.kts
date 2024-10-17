import app.threedollars.manager.configureHiltAndroid
import app.threedollars.manager.configureKotlinAndroid
import app.threedollars.manager.libs
import gradle.kotlin.dsl.accessors._2fb5859a04200edaf14b854c40b2e363.implementation

plugins {
    id("com.android.application")
    id("threedollars.android.compose")
}

configureKotlinAndroid()
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
