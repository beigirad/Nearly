import ir.beigirad.dependencies.Libraries
import ir.beigirad.dependencies.Modules
import ir.beigirad.dependencies.Versions

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Versions.compileSdk)

    defaultConfig {
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
    }
}

dependencies {
    implementation(Libraries.kotlin)
    implementation(Libraries.timber)

    implementation(Libraries.rxJava)
    implementation(Libraries.rxkotlin)
    implementation(Libraries.dagger)
    kapt(Libraries.daggerCompiler)

    implementation(project(Modules.domain))
}