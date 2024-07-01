plugins {
    val kotlinVersion = "2.0.0"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    id("org.springframework.boot") version "3.3.0"
}

group = "hu.kotlin.hazifeladat.ms"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.13.11")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

springBoot {
    mainClass = if (project.hasProperty("web")) "hu.vanio.kotlin.hazifeladat.ms.web.WeatherWebAppKt" else "hu.vanio.kotlin.hazifeladat.ms.cli.WeatherAppKt"
}
