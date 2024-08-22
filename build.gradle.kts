buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Kotlin.GRADLE)
        classpath(Dependencies.Kotlin.KOTLIN_GRADLE)
        classpath(Dependencies.Hilt.HILT_PLUGIN)
        classpath(Dependencies.Firebase.GOOGLE_SERVICE)
        classpath(Dependencies.Firebase.FIREBASE_CRASHLYTICS)
        classpath(Dependencies.Firebase.FIREBASE_DISTRIBUTION)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()

        maven(url = "https://devrepo.kakao.com/nexus/content/groups/public/")
        maven(url = "https://repository.map.naver.com/archive/maven")
    }
}

task("clean", Delete::class) {
    delete = setOf(rootProject.buildDir)
}