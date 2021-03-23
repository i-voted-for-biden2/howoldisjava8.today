plugins {
  kotlin("jvm") version "1.4.32"
  kotlin("plugin.serialization") version "1.4.32"
  application
}

repositories {
  mavenCentral()
  jcenter()
}

dependencies {
  implementation("io.github.cdimascio", "dotenv-kotlin", "6.2.2")
  implementation("io.ktor", "ktor-client-okhttp", "1.5.2")
  implementation("io.ktor", "ktor-client-serialization", "1.5.2")
  implementation("com.github.redouane59.twitter", "twittered", "1.20")
  implementation("org.jetbrains.kotlinx", "kotlinx-datetime", "0.1.1")

  implementation("org.slf4j", "slf4j-simple", "1.7.30")

  implementation("dev.inmo", "krontab", "0.5.0")
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
