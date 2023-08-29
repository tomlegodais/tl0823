plugins {
    java
}

subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    val junitVersion = "5.10.0"
    val lombokVersion = "1.18.28"

    dependencies {
        compileOnly("org.projectlombok:lombok:$lombokVersion")
        annotationProcessor("org.projectlombok:lombok:$lombokVersion")

        implementation(enforcedPlatform("org.junit:junit-bom:$junitVersion"))

        testImplementation("org.junit.jupiter:junit-jupiter")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

        testCompileOnly("org.projectlombok:lombok:$lombokVersion")
        testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
    }

    tasks {
        withType<JavaCompile> {
            sourceCompatibility = "11"
            targetCompatibility = "11"
        }

        named<Test>("test") {
            useJUnitPlatform()
        }
    }
}