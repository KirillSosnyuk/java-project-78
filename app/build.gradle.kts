plugins {
    id("application")
    id("com.github.ben-manes.versions") version "0.47.0"
    id("org.sonarqube") version "6.3.1.5724"
    checkstyle
    jacoco
}

jacoco {
    toolVersion = "0.8.12"
}

sonar {
    properties {
        property("sonar.projectKey", "KirillSosnyuk_java-project-78")
        property("sonar.organization", "kirillsosnyuk")
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            layout.buildDirectory.file("reports/jacoco/test/jacocoTestReport.xml")
                .get()
                .asFile
                .absolutePath
        )

        // JUnit XML report
        property(
            "sonar.junit.reportPaths",
            layout.buildDirectory.dir("test-results/test")
                .get()
                .asFile
                .absolutePath
        )
    }
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    reports {
        junitXml.required.set(true)
        junitXml.outputLocation.set(layout.buildDirectory.dir("test-results/test"))
        html.required.set(true)
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        xml.outputLocation.set(file("/reports/jacoco/test/jacocoTestReport.xml"))
        html.required.set(true)
    }
}