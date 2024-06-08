group = "dev.fathony.anvil-helper"

plugins {
    kotlin("jvm")
    kotlin("kapt")
    `java-library`
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.anvil.compiler.api)
    implementation(libs.anvil.compiler.utils)
    implementation(project(":api"))

    implementation(libs.dagger.runtime)
    compileOnly(libs.autoService.annotations)
    kapt(libs.autoService.processor)

    implementation(libs.kotlinPoet.lib)
    implementation(libs.kotlinPoet.metadata)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.truth)
    testImplementation(testFixtures(libs.anvil.compiler.utils))
}
