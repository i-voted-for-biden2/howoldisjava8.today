plugins {
  kotlin("jvm") version "1.4.32"
  kotlin("plugin.serialization") version "1.4.32"
  application
}

repositories {
  mavenCentral()
  maven("https://jitpack.io")
}

dependencies {
  implementation("io.ktor", "ktor-server-core", "1.5.3")
  implementation("io.ktor", "ktor-server-netty", "1.5.3")
  implementation("io.ktor", "ktor-serialization", "1.5.3")
  implementation("io.ktor", "ktor-locations", "1.5.3")
  testImplementation("io.ktor", "ktor-server-test-host", "1.5.3")

  implementation("io.insert-koin", "koin-ktor", "3.0.1-beta-2")
  implementation("io.insert-koin", "koin-logger-slf4j", "3.0.1-beta-2")
  testImplementation("io.insert-koin", "koin-test", "3.0.1-beta-2")

  implementation("guru.zoroark", "ktor-rate-limit", "v0.0.1")

  implementation("org.jetbrains.kotlinx", "kotlinx-datetime", "0.1.1")

  implementation("org.jetbrains.kotlinx", "kotlinx-serialization-json", "1.1.0")

  implementation("ch.qos.logback", "logback-classic", "1.2.3")

  testImplementation(kotlin("test-junit5"))
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}

tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      jvmTarget = "11"
      freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
  }
  test {
    useJUnitPlatform()
  }
}

application {
  mainClass.set("today.howoldisjava8.api.LauncherKt")
}
