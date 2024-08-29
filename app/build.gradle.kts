import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)

}

val localPropertiesFile = rootProject.file("local.properties")
val localProperties = Properties()
localProperties.load(FileInputStream(localPropertiesFile))

val versionMajor = 1 // 0~9
val versionMinor = 0 // 0~99
val versionPatch = 0 // 0~99
val versionHotfix = 1 // 0~99

val versionCodeFinal =
    versionMajor * 10_000_000 + versionMinor * 100_000 + versionPatch * 1000 + versionHotfix
val versionNameFinal = "$versionMajor.$versionMinor.$versionPatch"



android {
    namespace = "kr.co.are.searchcocktail"
    compileSdk = 34

    defaultConfig {
        applicationId = "kr.co.are.searchcocktail"
        minSdk = 26
        targetSdk = 34
        versionCode = versionCodeFinal
        versionName = versionNameFinal

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    signingConfigs {
        create("config") {
            keyAlias = localProperties["KEY_ALIAS"] as String
            keyPassword = localProperties["KEY_PASSWORD"] as String
            storeFile = file(localProperties["STORE_FILE"] as String)
            storePassword = localProperties["STORE_PASSWORD"] as String
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("config")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "2.0.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core:build-config"))
    implementation(project(":core:navigation"))

    implementation(project(":data:remote-api-cocktail"))
    implementation(project(":data:remote-api-stream-text"))
    implementation(project(":data:local-room-cocktail"))

    implementation(project(":domain"))
    implementation(project(":feature:search"))
    implementation(project(":feature:stream-text"))
    implementation(project(":feature:detail"))
    implementation(project(":feature:favorite"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.navigation.runtime.ktx)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)

    //Navigation
    implementation(libs.navigation.compose)

    //Retrofit
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.moshi)
    implementation(libs.retrofit2.adapter.rxjava3)
    implementation(libs.moshi.kotlin)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.okhttp)

    //Logger
    implementation(libs.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}