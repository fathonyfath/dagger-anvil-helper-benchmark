plugins {
    id("dev.fathony.android-application")
    id("dev.fathony.dagger")
    id("dev.fathony.dagger-kapt")
    id("dev.fathony.anvil")
}

android {
    namespace = "dev.fathony.anvilhelper.anvil"

    defaultConfig {
        applicationId = "dev.fathony.anvilhelper.anvil"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(":base"))
    implementation(project(":common"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // region Subproject modules
    // endregion
}
