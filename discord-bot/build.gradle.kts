import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.4.31"
  kotlin("kapt") version "1.4.31"
  id("io.gitlab.arturbosch.detekt") version "1.15.0"
  application
}

group = "today.howoldisjava8"
version = "1.0-SNAPSHOT"

repositories {
  // Required for Kord and Kotlin
  mavenCentral()
  // Currently only required for koin (Kordx.commands) dependency due to weird Gradle bug
  jcenter()
  // Kotlinx.date-time
  maven("https://kotlin.bintray.com/kotlinx/")
  // Kord snapshots
  maven("https://oss.sonatype.org/content/repositories/snapshots")
  // Lavalink.kt / Lavakord
  maven("https://schlaubi.jfrog.io/artifactory/lavakord")
}

// This is just here so I can get the latest lavakord update
configurations.all {
  resolutionStrategy.cacheDynamicVersionsFor(1, "minutes")
}

application {
  mainClass.set("today.howoldisjava8.discord_bot.LauncherKt")
}

dependencies {
  // See https://github.com/kordlib/kord#gradle-kotlin
  implementation("dev.kord", "kord-core", "0.7.0-SNAPSHOT")

  // See https://github.com/DRSchlaubi/Lavalink.kt/tree/feature/mpp
  implementation("dev.schlaubi.lavakord", "kord", "1.0.0-SNAPSHOT")

  // See https://github.com/kordlib/kordx.commands#gradle-kotlin
  implementation("dev.kord.x", "commands-runtime-kord", "0.4.0-SNAPSHOT")
  kapt("dev.kord.x", "commands-processor", "0.4.0-SNAPSHOT")

  implementation("org.jetbrains.kotlinx", "kotlinx-datetime", "0.1.1")

  implementation("org.slf4j", "slf4j-simple", "1.7.30")

  // Add ktlint
  detektPlugins("io.gitlab.arturbosch.detekt", "detekt-formatting", "1.15.0")
}

tasks {
  withType<KotlinCompile> {
    kotlinOptions {
      jvmTarget = "1.8" // Kord uses Java 8 features for Kord/JVM so we need to enable this
      freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn" // required for slash commands
    }
  }
}
