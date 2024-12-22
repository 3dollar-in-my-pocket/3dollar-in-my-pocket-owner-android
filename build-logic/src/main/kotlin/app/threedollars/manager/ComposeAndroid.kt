package app.threedollars.manager

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


internal fun Project.configureComposeAndroid() {
    val libs = extensions.libs

    pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

    androidExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("kotlin").get().toString()
        }


        dependencies {
            val bom = libs.findLibrary("androidx.compose.bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))

            add("implementation", libs.findLibrary("material3").get())
            add("implementation", libs.findLibrary("androidx.compose.ui").get())
            add("implementation", libs.findLibrary("androidx.compose.ui.tooling.preview").get())
            add("androidTestImplementation", libs.findLibrary("androidx.test.ext").get())
            add("androidTestImplementation", libs.findLibrary("androidx.test.espresso.core").get())
            add("androidTestImplementation", libs.findLibrary("androidx.compose.ui.test").get())
            add("debugImplementation", libs.findLibrary("androidx.compose.ui.tooling").get())
            add("debugImplementation", libs.findLibrary("androidx.compose.ui.test.manifest").get())
        }
    }
}
