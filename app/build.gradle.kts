import ir.beigirad.dependencies.*
import java.util.*
import java.io.*

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Versions.compileSdk)
    buildToolsVersion(Versions.buildTools)

    defaultConfig {
        applicationId = ApplicationId.id
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)

        versionCode = Releases.versionCode
        versionName = Releases.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Libraries.kotlin)
    implementation(Libraries.timber)

    implementation(SupportLibraries.appcompat)
    implementation(SupportLibraries.design)
    implementation(SupportLibraries.constraintlayout)


    implementation(Libraries.lifecucleRuntime)
    implementation(Libraries.lifecycleExtensions)
    kapt(Libraries.lifecycleCompiler)

    implementation(Libraries.rxkotlin)
    implementation(Libraries.rxAndroid)

    implementation(Libraries.dagger)
    kapt(Libraries.daggerCompiler)
    implementation(Libraries.daggerAndroid)
    kapt(Libraries.daggerAndroidProcessor)
    implementation(Libraries.daggerAndroidSupport)

    implementation(Libraries.multidex)

    implementation(Libraries.glide)
    kapt(Libraries.glideCompiler)

    implementation(project(Modules.domain))
    implementation(project(Modules.data))
    implementation(project(Modules.presentation))

    implementation(Libraries.room)
    kapt(Libraries.roomCompiler)
    implementation(Libraries.roomRx)

    implementation(Libraries.stethoInterceptor)

}
