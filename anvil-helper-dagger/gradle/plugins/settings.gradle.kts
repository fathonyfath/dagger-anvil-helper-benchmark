@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("../meta-plugins")
}

dependencyResolutionManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    
    versionCatalogs { 
        create("libs") {
            from(files("../libs.versions.toml"))
        }
    }
}

include("android-plugins", "dagger-plugins")
