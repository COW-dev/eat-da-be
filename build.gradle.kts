
import com.epages.restdocs.apispec.gradle.OpenApi3Task
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.epages.restdocs-api-spec")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

group = "${property("projectGroup")}"
version = "${property("applicationVersion")}"

val javaVersion = "${property("javaVersion")}"
val testContainerVersion = "${property("testContainerVersion")}"
val restdocsApiVersion = "${property("restdocsApiVersion")}"
val springMockkVersion = "${property("springMockkVersion")}"
val autoParamsVersion = "${property("autoParamsVersion")}"

java {
    sourceCompatibility = JavaVersion.valueOf("VERSION_$javaVersion")
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // spring
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // convert
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // container
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")

    // database
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.liquibase:liquibase-core")

    // test
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:testcontainers:$testContainerVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testContainerVersion")
    testImplementation("org.testcontainers:jdbc:$testContainerVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("com.epages:restdocs-api-spec-mockmvc:$restdocsApiVersion")
    testImplementation("com.ninja-squad:springmockk:$springMockkVersion")
    testImplementation("io.github.autoparams:autoparams-kotlin:$autoParamsVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = javaVersion
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register<Copy>("copyOasToSwagger") {
    from("$buildDir/api-spec/openapi3.yaml")
    into("src/main/resources/static/swagger-ui/.")
    dependsOn("openapi3")
}

tasks.withType<OpenApi3Task> {
    finalizedBy("copyOasToSwagger")
}

openapi3 {
    setServer("http://localhost:8080")
    title = "Eatda API Documentation"
    description = "Eatda(잇다) 서비스의 API 명세서입니다."
    version = "0.0.1"
    format = "yaml"
}
