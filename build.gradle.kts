import com.epages.restdocs.apispec.gradle.OpenApi3Task
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.meta.jaxb.Logging
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.epages.restdocs-api-spec")
    id("nu.studer.jooq")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    jacoco
}

group = "${property("projectGroup")}"
version = "${property("applicationVersion")}"

val javaVersion = "${property("javaVersion")}"
val testContainerVersion = "${property("testContainerVersion")}"
val restdocsApiVersion = "${property("restdocsApiVersion")}"
val springMockkVersion = "${property("springMockkVersion")}"
val autoParamsVersion = "${property("autoParamsVersion")}"
val jacocoVersion = "${property("jacocoVersion")}"
val jooqVersion = "${property("jooqVersion")}"

tasks.withType<Jar> {
    enabled = false
}

tasks.withType<BootJar> {
    enabled = true
}

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
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // convert
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // database
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.liquibase:liquibase-core")
    jooqGenerator("org.jooq:jooq-meta-extensions-liquibase")
    jooqGenerator("org.liquibase:liquibase-core")

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

jooq {
    version.set(dependencyManagement.importedProperties["jooq.version"])

    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = Logging.WARN
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.extensions.liquibase.LiquibaseDatabase"
                        properties.add(
                            org.jooq.meta.jaxb.Property().withKey("rootPath")
                                .withValue("${project.projectDir}/src/main/resources")
                        )
                        properties.add(
                            org.jooq.meta.jaxb.Property().withKey("scripts")
                                .withValue("/db/changelog-master.yml")
                        )
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = true
                        isFluentSetters = true
                    }
                    target.apply {
                        packageName = "com.mjucow.eatda.jooq"
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    extensions.configure(JacocoTaskExtension::class) {
        setDestinationFile(file("$buildDir/jacoco/jacoco.exec"))
    }

    finalizedBy(tasks.jacocoTestReport)
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

jacoco {
    toolVersion = jacocoVersion
}

tasks.jacocoTestReport {
    reports {
        html.required = true
        xml.required = true
        csv.required = false
    }

    dependsOn(tasks.test)
    finalizedBy("jacocoTestCoverageVerification")
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            // 'element'가 없으면 프로젝트의 전체 파일을 합친 값을 기준으로 한다.
            limit {
                // 'counter'를 지정하지 않으면 default는 'INSTRUCTION'
                // 'value'를 지정하지 않으면 default는 'COVEREDRATIO'
                minimum = "0.30".toBigDecimal()
            }
        }

        rule {
            enabled = true

            // 룰을 체크할 단위는 클래스 단위
            element = "CLASS"

            // 브랜치 커버리지를 최소한 80% 만족시켜야 한다.
            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = "0.80".toBigDecimal()
            }

            // 라인 커버리지를 최소한 80% 만족시켜야 한다.
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.70".toBigDecimal()
            }

            // 빈 줄을 제외한 코드의 라인수를 최대 200라인으로 제한한다.
            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "200".toBigDecimal()
            }

            // 커버리지 체크를 제외할 클래스들
            excludes = listOf(
                "com.mjucow.eatda.EatdaApplicationKt",
                "*.common.*",
                "*.dto.*",
                "com.mjucow.eatda.jooq.*",
                "*.Companion"
            )
        }
    }
}

val testCoverage by tasks.registering {
    group = "verification"
    description = "Runs the unit tests with coverage"

    dependsOn(
        ":test",
        ":jacocoTestReport",
        ":jacocoTestCoverageVerification"
    )

    tasks["jacocoTestReport"].mustRunAfter(tasks["test"])
    tasks["jacocoTestCoverageVerification"].mustRunAfter(tasks["jacocoTestReport"])
}
