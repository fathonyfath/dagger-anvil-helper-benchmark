@file:Suppress("UnstableApiUsage")

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

include("android-plugins")
