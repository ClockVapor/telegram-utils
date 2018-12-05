import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    maven
    kotlin("jvm") version "1.3.10"
}

group = "clockvapor"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("io.github.seik", "kotlin-telegram-bot", "0.3.5")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
