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
include(":core")
include(":core:ui")
include(":core:navigation-api")
include(":core:navigation-impl")
include(":core:network")
include(":core:utils")
include(":core:analytics")
include(":core:auth")
include(":core:cache")
include(":feature")
include(":feature:auth")
include(":feature:movies")
include(":feature:splash")
