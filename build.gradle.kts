plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.serialization") version "2.0.20"

    `java-gradle-plugin`

    `maven-publish`
}

group = "moe.shinkiro"
version = "0.1.2"

repositories {
    mavenCentral()
}

dependencies {

    implementation("com.gradleup.shadow:shadow-gradle-plugin:8.3.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.2")

}

kotlin {
    jvmToolchain(8)
    explicitApi()
}

gradlePlugin {

    val silicone by plugins.creating {
        id = "moe.shinkiro.silicone"
        implementationClass = "moe.shinkiro.silicone.SiliconePlugin"
    }

}

publishing {
    repositories {
        mavenLocal()
    }
}