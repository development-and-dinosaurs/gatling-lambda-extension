import java.time.Duration
import java.util.Base64
plugins {
    scala
    signing
    id("com.diffplug.spotless") version "5.8.1"
    id("io.toolebox.git-versioner") version "1.6.5"
    id("io.codearte.nexus-staging") version "0.22.0"
    id("de.marcphilipp.nexus-publish") version "0.4.0"
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

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name.set("Gatling Lambda Extension") 
                description.set("A Gatling extension for invoking lambda functions.")
                url.set("https://github.com/toolebox-io/gatling-lambda-extension")
                licenses {
                    license {
                        name.set("The MIT License (MIT)")
                        url.set("http://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        name.set("Sean O'Toole")
                        email.set("sean@seanotoole.co.uk")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/toolebox-io/gatling-lambda-extension.git")
                    developerConnection.set("scm:git@github.com:toolebox-io/gatling-lambda-extension.git")
                    url.set("https://github.com/toolebox-io/gatling-lambda-extension/")
                }
            }
        }
    }
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    val decodedKey = decode(signingKey)
    useInMemoryPgpKeys(decodedKey, signingPassword)
    sign(publishing.publications["mavenJava"])
}

fun decode(value: String?) =
    if (value == null) "" else String(Base64.getDecoder().decode(value))

tasks.withType<Sign>().configureEach {
    onlyIf { project.hasProperty("release") }
}

nexusStaging {
    username = project.findProperty("sonatypeUsername") as String?
    password = project.findProperty("sonatypePassword") as String?
    packageGroup = project.group.toString()
    numberOfRetries = 60
    delayBetweenRetriesInMillis = 5000
}

nexusPublishing {
    connectTimeout.set(Duration.ofMinutes(5))
    clientTimeout.set(Duration.ofMinutes(5))
    repositories {
        sonatype()
    }
}
