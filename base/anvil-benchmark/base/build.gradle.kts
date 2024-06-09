plugins {
    id("dev.fathony.android-library")
    id("dev.fathony.dagger")
}

android {
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
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
    implementation(libs.javax.inject)
    implementation(libs.anvilHelper.api)
}
