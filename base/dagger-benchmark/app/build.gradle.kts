plugins {
    id("dev.fathony.android-application")
    id("dev.fathony.dagger")
}



android {
    namespace = "dev.fathony.anvilhelper.dagger"

    defaultConfig {
        applicationId = "dev.fathony.anvilhelper.dagger"
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
