plugins {
    id("threedollars.android.library")
}

android {
    namespace = "app.threedollars.domain"
}
dependencies {
    implementation(projects.common)
    implementation(libs.okhttp3)
    implementation(libs.paging)
    implementation(libs.paging.compose)
}