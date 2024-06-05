plugins { 
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "dev.fathony.anvilhelper.${project.name}"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
        targetSdk = 34
        applicationId = "dev.fathony.anvilhelper.${project.name}"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}
