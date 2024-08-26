plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "kr.co.are.searchcocktail.core.buildconfig"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_API_URL_COCKTAIL", "\"https://www.thecocktaildb.com/api/json/\"")
            buildConfigField("String", "BASE_API_URL_STREAM_TEXT", "\"https://api.push-knock.com/\"")
            buildConfigField("String", "KEY_STREAM_TEXT_API_KEY", "\"${project.findProperty("KEY_STREAM_TEXT_API_KEY")}\"")

        }
        release {
            buildConfigField("String", "BASE_API_URL_COCKTAIL", "\"https://www.thecocktaildb.com/api/json/\"")
            buildConfigField("String", "BASE_API_URL_STREAM_TEXT", "\"https://api.push-knock.com/\"")
            buildConfigField("String", "KEY_STREAM_TEXT_API_KEY", "\"${project.findProperty("KEY_STREAM_TEXT_API_KEY")}\"")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}