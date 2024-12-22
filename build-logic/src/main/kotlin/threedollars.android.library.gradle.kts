import app.threedollars.manager.configureCoroutineAndroid
import app.threedollars.manager.configureHiltAndroid
import app.threedollars.manager.configureKotlinAndroid
import gradle.kotlin.dsl.accessors._4b055a01bae563bd2c86a468691a3401.testImplementation

plugins {
    id("com.android.library")
}

configureKotlinAndroid()
configureCoroutineAndroid()
configureHiltAndroid()