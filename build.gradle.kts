import net.darkmeow.dark_maven.mavenDarkMeow

val baseGroup: String by project
val baseVersion: String by project
val kotlinVersion: String by project

allprojects {
    group = baseGroup
    version = baseVersion
}

plugins {
    kotlin("jvm")
    `java-gradle-plugin`
    `maven-publish`

    id("net.darkmeow.dark-maven") version "1.0"
}

allprojects {
    apply {
        plugin("java")
        plugin("kotlin")
        plugin("maven-publish")
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(8))
        }
    }

    publishing {
        repositories {
            mavenLocal()
            mavenDarkMeow()
        }
    }
}

subprojects {
    base {
        archivesName.set("${rootProject.name}-${project.name}")
    }
}

java {
    withSourcesJar()
}

gradlePlugin {
    plugins {
        create("ktgen") {
            id = "$baseGroup.ktgen"
            displayName = "ktgen"
            implementationClass = "net.darkmeow.kt_gen.KtGenPlugin"
        }
    }
}

tasks {
    processResources {
        expand("version" to project.version)
    }
}