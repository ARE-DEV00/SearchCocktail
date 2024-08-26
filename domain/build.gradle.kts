plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies{
    implementation(libs.kotlinx.coroutines.core)

    //Dagger
    implementation(libs.dagger)

    //Moshi - Json Parser
    implementation(libs.moshi.kotlin)

}