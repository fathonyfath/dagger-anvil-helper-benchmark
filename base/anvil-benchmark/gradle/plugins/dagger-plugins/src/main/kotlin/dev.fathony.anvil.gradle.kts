plugins {
    id("com.squareup.anvil")
}

val libs: VersionCatalog = the<VersionCatalogsExtension>().named("libs")

dependencies {
    "implementation"(libs.findLibrary("anvilHelper.api").get())
    "anvil"(libs.findLibrary("anvilHelper.processor").get())
}
