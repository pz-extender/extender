plugins {
    kotlin("jvm")
    `maven-publish`
    signing
    `java-library`
}

repositories {
    pzLocal()
}

dependencies {
    compileOnlyApi(pzGameApi())
    compileOnlyApi(pzGameLibs())

    api(kotlin("script-runtime"))
    implementation(kotlin("stdlib"))
    implementation(kotlin("scripting-jvm"))

    implementation("com.google.guava:guava:31.0.1-jre")
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])

        pom {
            packaging = "jar"
            name.set("Project Zomboid Extender API")
            description.set(
            """
                Public API available to extensions loaded by the Project Zomboid Extender Framework.
            """.trimIndent()
            )
        }
    }
}

signing {
    sign(publishing.publications["maven"])
}