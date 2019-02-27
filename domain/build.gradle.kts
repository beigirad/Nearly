import ir.beigirad.dependencies.Libraries

plugins {
    id("kotlin")
}

dependencies {
    implementation(Libraries.kotlin)

    implementation(Libraries.rxJava)
    implementation(Libraries.rxkotlin)
    implementation(Libraries.dagger)
    kapt(Libraries.daggerCompiler)
}