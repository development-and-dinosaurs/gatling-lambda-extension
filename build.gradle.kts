plugins {
    scala
    `maven-publish`
    id("com.diffplug.gradle.spotless") version "3.27.1"
    id("io.toolebox.git-versioner") version "1.3.0"
}

group = "io.toolebox"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scala-lang:scala-library:2.12.10")
    implementation("io.gatling:gatling-core:3.3.1")
    implementation("software.amazon.awssdk:lambda:2.10.50")
}

spotless {
    scala {
        scalafmt()
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}
