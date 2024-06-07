plugins {
    kotlin("kapt")
}

val libs = the<VersionCatalogsExtension>().named("libs")

dependencies {
    "implementation"(libs.findLibrary("dagger.runtime").get())
    "kapt"(libs.findLibrary("dagger.compiler").get())
}
