plugins {
    java
    alias(libs.plugins.spring.boot)
}

java {
    toolchain {
        languageVersion.set(
            JavaLanguageVersion.of(libs.versions.java.get().toInt())
        )
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.boot.starter.web)

    testImplementation(libs.spring.boot.starter.test)
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
}
