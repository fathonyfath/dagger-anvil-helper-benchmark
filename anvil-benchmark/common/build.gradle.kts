plugins {
    id("dev.fathony.android-library")
    id("dev.fathony.dagger")
    id("dev.fathony.anvil")
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
    implementation(project(":base"))
}

anvil {
    generateDaggerFactories = true
}
