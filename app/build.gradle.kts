plugins {
    application
}

application {
    mainClass.set("com.tomlegodais.app.ToolRentalApplication")
}

dependencies {
    implementation(project(":shared"))

    testImplementation("com.google.truth:truth:1.1.5")
}