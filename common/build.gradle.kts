plugins {
    id("threedollars.android.feature")
}


android {
    namespace = "app.threedollars.common"
}

dependencies {
    implementation(libs.play.services.measurement.api)
    implementation(libs.gson)
}