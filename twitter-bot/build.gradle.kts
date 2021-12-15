plugins {
  kotlin("jvm") version "1.5.31"
  kotlin("plugin.serialization") version "1.6.10"
  application
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("io.github.cdimascio", "dotenv-kotlin", "6.2.2")
  implementation(platform("io.ktor:ktor-bom:1.6.6"))
  implementation("io.ktor", "ktor-client-okhttp")
  implementation("io.ktor", "ktor-client-serialization")
  implementation("com.github.redouane59.twitter", "twittered", "1.26")
  implementation("org.jetbrains.kotlinx", "kotlinx-datetime", "0.2.1")

  implementation("org.slf4j", "slf4j-simple", "1.7.30")

  implementation("dev.inmo", "krontab", "0.6.1")
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
