plugins {
    id("threedollars.android.feature")
    id("kotlinx-serialization")
}

android {
    namespace = "app.threedollars.manager.feature.storemanagement"
}

dependencies {
    implementation(projects.common)
    implementation(projects.data)
    implementation(projects.domain)

    implementation(libs.firebase.messaging.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.moshi.kotlin)
    implementation(libs.compose.coil)
    implementation(libs.dialog.compose)
    implementation(libs.dialog.time.compose)
    implementation(libs.paging.compose)
}