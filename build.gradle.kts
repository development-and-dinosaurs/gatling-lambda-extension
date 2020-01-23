import java.time.Duration
plugins {
    scala
    signing
    id("com.diffplug.gradle.spotless") version "3.27.1"
    id("io.toolebox.git-versioner") version "1.3.0"
    id("io.codearte.nexus-staging") version "0.21.2"
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

val sonatypeUsername: String? by project
val sonatypePassword: String? by project

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
    repositories {
        maven {
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = sonatypeUsername
                password = sonatypePassword
            }
        }
    }
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications["mavenJava"])
}

tasks.withType<Sign>().configureEach {
    onlyIf { project.findProperty("release") == "true" }
}

nexusStaging {
    username = sonatypeUsername
    password = sonatypePassword
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
