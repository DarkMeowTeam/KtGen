rootProject.name = "ktgen"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        maven("https://nekocurit.asia/repository/release/")
    }

    val kotlinVersion: String by settings

    plugins {
        id("org.jetbrains.kotlin.jvm").version(kotlinVersion)
    }
}

include("api")
include("runtime")