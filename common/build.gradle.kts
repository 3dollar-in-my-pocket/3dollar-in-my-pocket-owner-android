plugins {
    id("threedollars.android.feature")
    id("kotlinx-serialization")
}


android {
    namespace = "app.threedollars.common"
}

dependencies {
    implementation(libs.play.services.measurement.api)
    implementation(libs.gson)
    implementation(libs.kotlinx.serialization.json)
}