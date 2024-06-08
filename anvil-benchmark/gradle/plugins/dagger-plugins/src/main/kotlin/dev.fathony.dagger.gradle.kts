val libs: VersionCatalog = the<VersionCatalogsExtension>().named("libs")

dependencies {
    "implementation"(libs.findLibrary("dagger.runtime").get())
}
