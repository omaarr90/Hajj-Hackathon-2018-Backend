plugins {
    java
    war
}

group = "io.fouad"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.mindrot", "jbcrypt", "0.4")
    implementation("com.google.crypto.tink", "tink", "1.2.0-rc3")
    implementation("io.jsonwebtoken", "jjwt", "0.9.1")
    {
        exclude("com.amazonaws", "aws-java-sdk-core")
        exclude("com.amazonaws", "aws-java-sdk-kms")
        exclude("com.google.api-client", "google-api-client")
        exclude("com.google.apis", "google-api-services-cloudkms")
        exclude("com.google.auto.service", "auto-service")
        exclude("com.google.protobuf", "protobuf-java")
    }
    compileOnly("javax", "javaee-api", "8.0")
    compileOnly("org.eclipse.persistence", "eclipselink", "2.7.2")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}