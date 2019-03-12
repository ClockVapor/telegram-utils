import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    maven
    kotlin("jvm") version "1.3.21"
}

group = "clockvapor.telegram"
version = "0.0.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("io.github.seik", "kotlin-telegram-bot", "0.3.6") {
        exclude("io.github.seik.kotlin-telegram-bot", "echo")
        exclude("io.github.seik.kotlin-telegram-bot", "dispatcher")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
