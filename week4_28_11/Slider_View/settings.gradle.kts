pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        // Có cũng được, không bắt buộc cho plugin:
        // maven("https://jitpack.io")
        maven("https://jitpack.io")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // Quan trọng: JCenter để lấy autoimageslider
        maven("https://jitpack.io")
    }
}

rootProject.name = "Slider_View"
include(":app")
