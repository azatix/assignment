plugins {
    kotlin("jvm") version "2.2.20"
    kotlin("plugin.serialization") version "2.3.20"
    id("io.qameta.allure") version "3.2.0"
}

repositories {
    mavenCentral()
    maven { url = uri("https://redirector.kotlinlang.org/maven/ktor-eap") }
}

val ktorVersion: String by project
val logbackVersion: String by project
val allureVersion: String by project

dependencies {
    implementation("io.ktor:ktor-client-core:${ktorVersion}")
    implementation("io.ktor:ktor-client-cio:${ktorVersion}")
    implementation("io.ktor:ktor-client-content-negotiation:${ktorVersion}")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${ktorVersion}")
    implementation("io.ktor:ktor-client-logging:${ktorVersion}")
    implementation("ch.qos.logback:logback-classic:${logbackVersion}")
    testImplementation(platform("io.qameta.allure:allure-bom:${allureVersion}"))
    testImplementation("io.qameta.allure:allure-junit5:${allureVersion}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

allure {
    version = allureVersion

    report {
        dependsOnTests.set(true)
    }

    adapter {
        autoconfigure.set(true)
        autoconfigureListeners.set(true)
        aspectjWeaver.set(true)
        frameworks {
            junit5 {
                enabled.set(true)
            }
        }
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform {
        val includeTag = project.findProperty("includeTag") as String?
        val excludeTag = project.findProperty("excludeTag") as String?

        if (!includeTag.isNullOrBlank()) {
            includeTags(*includeTag.split(",").map { it.trim() }.toTypedArray())
        }

        if (!excludeTag.isNullOrBlank()) {
            excludeTags(*excludeTag.split(",").map { it.trim() }.toTypedArray())
        }
    }
}