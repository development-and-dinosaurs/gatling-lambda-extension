plugins {
    scala
    id("com.diffplug.gradle.spotless") version "3.27.1"
    id("io.toolebox.git-versioner") version "1.3.0"
}

group = "io.toolebox"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scala-lang:scala-library:2.12.10")
}

spotless {
    scala {
        scalafmt()
    }
}
