plugins {
    kotlin("kapt")
}

val libs: VersionCatalog = the<VersionCatalogsExtension>().named("libs")

dependencies {
    "kapt"(libs.findLibrary("dagger.compiler").get())
}
