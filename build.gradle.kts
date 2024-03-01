import java.util.Base64

plugins {
    `maven-publish`
    scala
    signing
    id("com.diffplug.spotless") version Versions.spotless
    id("io.github.gradle-nexus.publish-plugin") version Versions.nexusPublish
    id("io.gatling.gradle") version Versions.gatling
    id("uk.co.developmentanddinosaurs.git-versioner") version Versions.gitVersioner
}

group = "uk.co.developmentanddinosaurs"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scala-lang:scala-library:${Versions.scala}")
    implementation("io.gatling:gatling-core:${Versions.gatling}")
    implementation("io.gatling:gatling-core-java:${Versions.gatling}")
    implementation("software.amazon.awssdk:lambda:${Versions.awsSdk}")
    implementation("com.softwaremill.quicklens:quicklens_2.13:1.9.7")

    gatling("software.amazon.awssdk:lambda:${Versions.awsSdk}")
}

spotless {
    scala {
        scalafmt()
    }
    java {
        googleJavaFormat()
    }
    kotlinGradle {
        ktlint()
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

versioner {
    startFrom {
        major = 3
        minor = 7
    }
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name.set("Gatling Lambda Extension")
                description.set("A Gatling extension for invoking lambda functions.")
                url.set("https://github.com/development-and-dinosaurs/gatling-lambda-extension")
                licenses {
                    license {
                        name.set("The MIT License (MIT)")
                        url.set("http://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        name.set("Tyrannoseanus")
                        email.set("tyrannoseanus@developmentanddinosaurs.co.uk")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/development-and-dinosaurs/lambda-gatling-extension.git")
                    developerConnection.set("scm:git@github.com:development-and-dinosaurs/lambda-gatling-extension.git")
                    url.set("https://github.com/development-and-dinosaurs/lambda-gatling-extension/")
                }
            }
        }
    }
}

signing {
    val signingKeyBase64: String? by project
    val signingKey = decode(signingKeyBase64)
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications["mavenJava"])
}

fun decode(base64Key: String?): String {
    return if (base64Key == null) "" else String(Base64.getDecoder().decode(base64Key))
}

tasks {
    withType<AbstractArchiveTask> {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }
    named<AbstractCompile>("compileScala") {
        classpath = sourceSets.main.get().compileClasspath
    }
    named<AbstractCompile>("compileJava") {
        classpath += files(sourceSets.main.get().scala.classesDirectory)
    }
}
