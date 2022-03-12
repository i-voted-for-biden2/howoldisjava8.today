plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    application
}

repositories {
    mavenCentral()
    maven("https://schlaubi.jfrog.io/artifactory/envconf/")
}

dependencies {
    implementation("io.github.redouane59.twitter", "twittered", "2.16")
    implementation("org.jetbrains.kotlinx", "kotlinx-datetime", "0.3.2")
    implementation("org.slf4j", "slf4j-simple", "1.7.36")
    implementation("dev.inmo", "krontab", "0.7.1")
    implementation("dev.schlaubi", "envconf", "1.1")
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
        }
    }
}

application {
    mainClass.set("today.howoldisjava8.twitterbot.LauncherKt")
}
