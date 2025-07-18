val kotlinPoetVersion: String by project

dependencies {
    implementation(project(":api"))
    runtimeOnly("com.squareup:kotlinpoet:$kotlinPoetVersion")
}

publishing {
    publications {
        create<MavenPublication>("ktgen-runtime") {
            from(components["java"])
            artifactId = "ktgen-runtime"
        }
    }
}