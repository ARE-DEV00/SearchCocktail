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

rootProject.name = "SearchCocktail"
include(":app")
include(":domain")
include(":feature:search")
include(":core:navigation")
include(":core:build-config")
include(":core:build-config-stub")
include(":data:remote-api-cocktail")
include(":data:remote-api-stream-text")
include(":feature:stream-text")
include(":core:youtube-player")
