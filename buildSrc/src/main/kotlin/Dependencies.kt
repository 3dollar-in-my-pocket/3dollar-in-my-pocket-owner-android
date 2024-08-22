import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    object Kotlin {
        const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}"
        const val GRADLE = "com.android.tools.build:gradle:${Versions.GRADLE}"
        const val KOTLIN_GRADLE = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
    }

    object Login {
        const val KAKAO_LOGIN = "com.kakao.sdk:v2-user:${Versions.KAKAO_LOGIN}"
    }

    object Naver {
        const val NAVER_MAP_COMPOSE = "io.github.fornewid:naver-map-compose:${Versions.NAVER_MAP_COMPOSE}"
    }

    object Android {
        const val DESUGAR_LIBS = "com.android.tools:desugar_jdk_libs:${Versions.DESUGAR_LIBS}"
        const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
        const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"
        const val LIFECYCLE_KTX =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_KTX}"
        const val DATASTORE = "androidx.datastore:datastore-preferences:${Versions.DATASTORE}"
        const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:${Versions.ANDROIDX_APPCOMPAT}"
        const val ANDROID_MATERIAL = "com.google.android.material:material:${Versions.ANDROID_MATERIAL}"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
        const val GOOGLE_LOCATION = "com.google.android.gms:play-services-location:${Versions.GOOGLE_LOCATION}"
    }

    object Firebase {
        const val GOOGLE_SERVICE = "com.google.gms:google-services:${Versions.GOOGLE_SERVICE}"
        const val FIREBASE_BOM = "com.google.firebase:firebase-bom:${Versions.FIREBASE_BOM}"
        const val FIREBASE_ANALYTICS = "com.google.firebase:firebase-analytics"
        const val FIREBASE_ANALYTICS_KTX = "com.google.firebase:firebase-analytics-ktx"
        const val FIREBASE_CRASHLYTICS = "com.google.firebase:firebase-crashlytics-gradle:${Versions.FIREBASE_CRASHLYTICS}"
        const val FIREBASE_CRASHLYTICS_KTX = "com.google.firebase:firebase-crashlytics-ktx"
        const val FIREBASE_DISTRIBUTION = "com.google.firebase:firebase-appdistribution-gradle:${Versions.FIREBASE_DISTRIBUTION}"
        const val FIREBASE_MESSAGING_KTX = "com.google.firebase:firebase-messaging-ktx:${Versions.FIREBASE_MESSAGING}"
    }

    object Compose {
        const val COMPOSE_ACTIVITY = "androidx.activity:activity-compose:${Versions.COMPOSE_ACTIVITY}"
        const val COMPOSE_FOUNDATION = "androidx.compose.foundation:foundation:${Versions.COMPOSE_MATERIAL}"
        const val COMPOSE_MATERIAL = "androidx.compose.material:material:${Versions.COMPOSE_MATERIAL}"
        const val COMPOSE_UI = "androidx.compose.ui:ui:${Versions.COMPOSE_MATERIAL}"
        const val COMPOSE_ANIMATION = "androidx.compose.animation:animation:${Versions.COMPOSE_MATERIAL}"
        const val COMPOSE_UI_TOOL = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE_MATERIAL}"
        const val COMPOSE_UI_TOOL_PREVIEW =
            "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE_MATERIAL}"
        const val COMPOSE_VIEWMODEL =
            "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.COMPOSE_LIFECYCLE_VIEWMODEL}"
        const val COMPOSE_NAVIGATION =
            "androidx.navigation:navigation-compose:${Versions.COMPOSE_NAVIGATION}"
        const val COMPOSE_LIFECYCLE = "androidx.lifecycle:lifecycle-runtime-compose:${Versions.COMPOSE_LIFECYCLE}"
        const val COMPOSE_COIL = "io.coil-kt:coil-compose:${Versions.COMPOSE_COIL}"
        const val COMPOSE_PERMISSIONS = "com.google.accompanist:accompanist-permissions:${Versions.COMPOSE_PERMISSIONS}"
    }

    object Paging {
        const val PAGING = "androidx.paging:paging-runtime:${Versions.PAGING}"
        const val PAGING_COMPOSE = "androidx.paging:paging-compose:${Versions.PAGING_COMPOSE}"
    }

    object Hilt {
        const val HILT_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}"
        const val HILT_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
        const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
        const val HILT_COMPOSE = "androidx.hilt:hilt-navigation-compose:${Versions.HILT_COMEPOSE}"
    }

    object Network {
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
        const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
        const val OKHTTP_LOGGER = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
        const val MOSHI = "com.squareup.moshi:moshi:${Versions.MOSHI}"
        const val MOSHI_KOTLIN = "com.squareup.moshi:moshi-kotlin:${Versions.MOSHI}"
        const val MOSHI_CONVERTER = "com.squareup.retrofit2:converter-moshi:${Versions.MOSHI_CONVERTER}"
        const val MOSHI_CODEGEN = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI}"
        const val GSON= "com.google.code.gson:gson:${Versions.GSON}"
    }

    object LOTTIE {
        const val LOTTIE_COMPOSE = "com.airbnb.android:lottie-compose:${Versions.LOTTIE_COMPOSE}"
    }

    object Other {
        const val DIALOG_COMPOSE = "io.github.vanpra.compose-material-dialogs:core:${Versions.DIALOG_COMPOSE}"
        const val DIALOG_TIME_COMPOSE = "io.github.vanpra.compose-material-dialogs:datetime:${Versions.DIALOG_COMPOSE}"
    }

    object Test {
        const val JUNIT = "junit:junit:${Versions.JUNIT}"
        const val ANDROIDX_JUNIT = "androidx.test.ext:junit:${Versions.ANDROIDX_JUNIT}"
        const val ANDROIDX_ESPRESSO = "androidx.test.espresso:espresso-core:${Versions.ANDROIDX_ESPRESSO}"
    }

    fun DependencyHandler.common() {
        coreLibraryDesugaring(Android.DESUGAR_LIBS)
        implementation(platform(Firebase.FIREBASE_BOM))
        implementation(Firebase.FIREBASE_ANALYTICS)
        implementation(Firebase.FIREBASE_ANALYTICS_KTX)
        implementation(Firebase.FIREBASE_CRASHLYTICS_KTX)
        implementation(Firebase.FIREBASE_MESSAGING_KTX)
        implementation(Kotlin.KOTLIN)
        implementation(Android.CORE_KTX)
        implementation(Android.LIFECYCLE_KTX)
        implementation(Android.FRAGMENT_KTX)
        implementation(Android.DATASTORE)
        implementation(Android.GOOGLE_LOCATION)
        implementation(Android.ANDROIDX_APPCOMPAT)
        implementation(Android.ANDROID_MATERIAL)
        implementation(Android.CONSTRAINT_LAYOUT)
        implementation(Compose.COMPOSE_ACTIVITY)
        implementation(Compose.COMPOSE_FOUNDATION)
        implementation(Compose.COMPOSE_MATERIAL)
        implementation(Compose.COMPOSE_ANIMATION)
        implementation(Compose.COMPOSE_UI_TOOL)
        implementation(Compose.COMPOSE_UI_TOOL_PREVIEW)
        implementation(Compose.COMPOSE_COIL)
        implementation(Compose.COMPOSE_VIEWMODEL)
        implementation(Compose.COMPOSE_NAVIGATION)
        implementation(Compose.COMPOSE_LIFECYCLE)
        implementation(Compose.COMPOSE_PERMISSIONS)
        implementation(Paging.PAGING)
        implementation(Paging.PAGING_COMPOSE)
        kapt(Hilt.HILT_COMPILER)
        implementation(Hilt.HILT_ANDROID)
        implementation(Hilt.HILT_COMPOSE)
        implementation(Test.JUNIT)
        androidTestImplementation(Test.ANDROIDX_JUNIT)
        androidTestImplementation(Test.ANDROIDX_ESPRESSO)
        implementation(Network.RETROFIT)
        implementation(Network.OKHTTP)
        implementation(Network.OKHTTP_LOGGER)
        implementation(Network.MOSHI)
        implementation(Network.MOSHI_KOTLIN)
        implementation(Network.MOSHI_CONVERTER)
        kapt(Network.MOSHI_CODEGEN)
        implementation(Login.KAKAO_LOGIN)
        implementation(Naver.NAVER_MAP_COMPOSE)
        implementation(LOTTIE.LOTTIE_COMPOSE)
        implementation(Other.DIALOG_COMPOSE)
        implementation(Other.DIALOG_TIME_COMPOSE)
    }

    fun DependencyHandler.implementation(dependency: Any) {
        add("implementation", dependency)
    }

    fun DependencyHandler.kapt(dependency: String) {
        add("kapt", dependency)
    }

    fun DependencyHandler.testImplementation(dependency: String) {
        add("testImplementation", dependency)
    }

    fun DependencyHandler.androidTestImplementation(dependency: String) {
        add("androidTestImplementation", dependency)
    }

    fun DependencyHandler.api(dependency: String) {
        add("api", dependency)
    }

    fun DependencyHandler.coreLibraryDesugaring(dependency: String) {
        add("coreLibraryDesugaring", dependency)
    }
}