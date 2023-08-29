plugins {
    application
}

application {
    mainClass.set("com.tomlegodais.infra.InfraApplication")
}

dependencies {
    implementation("software.amazon.awscdk:aws-cdk-lib:2.93.0")
    implementation("software.constructs:constructs:10.2.69")
}