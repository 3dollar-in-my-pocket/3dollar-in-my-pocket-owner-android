plugins {
    id("threedollars.android.feature")
    id("com.google.gms.google-services")
    id("kotlinx-serialization")
}

android {
    namespace = "app.threedollars.manager.feature.home"
}

dependencies {
    implementation(projects.common)
    implementation(projects.data)
    implementation(projects.domain)

    implementation(libs.firebase.messaging.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.compose.permissions)
    implementation(libs.naver.map.compose)
    implementation(libs.google.location)
    implementation(libs.moshi.kotlin)
}