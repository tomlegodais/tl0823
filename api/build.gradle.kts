import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "2.7.15"
    id("io.spring.dependency-management") version "1.1.3"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("com.amazonaws:aws-java-sdk-secretsmanager:1.12.538")
    implementation("org.json:json:20230618")
    implementation("org.postgresql:postgresql:42.6.0")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    implementation(project(":shared"))

    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-core:5.5.0")
    testImplementation("com.google.truth:truth:1.1.5")
}

tasks.withType<BootJar> {
    archiveFileName.set("tl0823-${project.name}.jar")
}