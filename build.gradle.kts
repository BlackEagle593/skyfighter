import org.apache.tools.ant.filters.ReplaceTokens
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.41"
    id("org.jlleitschuh.gradle.ktlint") version "8.2.0"
    id("com.github.johnrengelman.shadow") version "5.1.0"
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compile(kotlin("stdlib"))
    compileOnly("com.destroystokyo.paper:paper-api:1.14.4-R0.1-SNAPSHOT")
    compileOnly("org.projectlombok:lombok:1.18.8")
    annotationProcessor("org.projectlombok:lombok:1.18.8")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_12.toString()
}

// Replace tokens in yml files
val tokens = mapOf(
    "GROUP" to project.group,
    "NAME" to project.name,
    "NAME_LC" to project.name.toLowerCase(),
    "DESCRIPTION" to project.description,
    "VERSION" to project.version
)
tasks.withType<ProcessResources> {
    filesMatching("*.yml") {
        filter<ReplaceTokens>("tokens" to tokens)
    }
}
