@file:Suppress("UnstableApiUsage")

package app.threedollars.manager

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import java.io.FileInputStream
import java.util.Properties

internal fun Project.configureKotlinAndroid() {
    val localProperties = Properties()
    localProperties.load(FileInputStream(rootProject.file("local.properties")))
    // Plugins
    pluginManager.apply {
        apply("org.jetbrains.kotlin.android")
    }

    // Android settings
    androidExtension.apply {
        compileSdk = 35

        defaultConfig {
            minSdk = 34
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            isCoreLibraryDesugaringEnabled = true
        }

        buildTypes {
            getByName("debug") {
            }

            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro",
                )
            }
        }
        buildFeatures {
            buildConfig = true
        }
    }

    with(extensions.getByType<KotlinAndroidProjectExtension>()) {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }

    val libs = extensions.libs

    dependencies {
        add("coreLibraryDesugaring", libs.findLibrary("android.desugarJdkLibs").get())
    }
}