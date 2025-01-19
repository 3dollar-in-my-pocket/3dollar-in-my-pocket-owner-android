pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven(url = "https://devrepo.kakao.com/nexus/content/groups/public/")
        maven(url = "https://repository.map.naver.com/archive/maven")
    }
}

gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:testClasses"))

rootProject.name = "ThreeDollarsManager"
include(
    ":app",

    ":data",
    ":domain",
    ":common",
    ":feature:home",
    ":feature:setting",
    ":feature:storemanagement",
    ":navigation"
)