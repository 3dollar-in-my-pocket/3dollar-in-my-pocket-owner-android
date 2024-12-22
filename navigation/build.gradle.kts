plugins {
    id("threedollars.android.feature")
    id("kotlinx-serialization")
}

android {
    namespace = "app.threedollars.manager.navigation"
}

dependencies {
    implementation(projects.feature.setting)
    implementation(projects.common)

    implementation(libs.kotlinx.serialization.json)
}