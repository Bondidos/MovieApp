pluginManagement {
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
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MovieApp"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")
include(":feature-auth")
include(":feature-movies")
include(":core")
include(":core:ui")
include(":core:navigation-api")
include(":core:navigation-impl")
include(":core:network")
include(":core:utils")
