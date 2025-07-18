val kotlinPoetVersion: String by project

java {
    withSourcesJar()
}

dependencies {
    api("com.squareup:kotlinpoet:$kotlinPoetVersion")
}

publishing {
    publications {
        create<MavenPublication>("ktgen-api") {
            from(components["java"])
            artifactId = "ktgen-api"
        }
    }
}