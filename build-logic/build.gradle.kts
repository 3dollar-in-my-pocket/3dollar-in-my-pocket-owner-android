plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    implementation(libs.gradle)
    implementation(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidHilt") {
            id = "threedollars.android.hilt"
            implementationClass = "app.threedollars.manager.HiltAndroidPlugin"
        }
        register("kotlinHilt") {
            id = "threedollars.kotlin.hilt"
            implementationClass = "app.threedollars.manager.HiltKotlinPlugin"
        }
    }
}
