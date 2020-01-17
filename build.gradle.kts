plugins {
    scala
    id("io.toolebox.git-versioner") version "1.3.0"
}

group = "io.toolebox"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scala-lang:scala-library:2.12.10")
}
