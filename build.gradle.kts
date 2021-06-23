import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.asciidoctor.convert") version "1.5.8"
    id("jacoco")

    kotlin("jvm") version "1.5.10"
    kotlin("plugin.spring") version "1.5.10"
    kotlin("plugin.jpa") version "1.5.10"
    kotlin("kapt") version "1.5.10"
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}


group = "com.wani"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

var snippetsDir = file("build/generated-snippets")

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")


}

dependencies {
    implementation("com.itextpdf:itext7-core:7.1.9")
    implementation("com.itextpdf:html2pdf:3.0.4")


    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.1.4")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.session:spring-session-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.springfox:springfox-swagger2:2.9.2")
    implementation("io.springfox:springfox-swagger-ui:2.9.2")
    implementation("com.querydsl:querydsl-jpa")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("mysql:mysql-connector-java")
    implementation("com.auth0:java-jwt:3.15.0")

    //jgraph
    implementation("org.jgrapht:jgrapht-core:1.0.1")

    //iamport
    implementation("com.github.iamport:iamport-rest-client-java:0.2.15")

    //jasypt
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.3")


    //handlebars
    implementation("pl.allegro.tech.boot:handlebars-spring-boot-starter:0.3.0")


    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.restdocs:spring-restdocs-asciidoctor:2.0.5.RELEASE")
    testImplementation("org.springframework.restdocs:spring-restdocs-restassured:2.0.5.RELEASE")
    testImplementation("io.rest-assured:rest-assured:3.3.0")

    kapt("com.querydsl:querydsl-apt:4.2.1:jpa")

    // 테스트 환경을 위한 embedded-redis 의존성 추가
    testImplementation("it.ozimov:embedded-redis:0.7.2")

    testImplementation("org.mockito:mockito-inline:3.6.28")
    testImplementation("io.rest-assured:rest-assured:4.2.1")
    testImplementation("io.rest-assured:json-path:4.2.1")
    testImplementation("io.rest-assured:xml-path:4.2.1")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:2.1.4")
    testImplementation("io.rest-assured:spring-mock-mvc:4.2.1")
}
tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.test {
    outputs.dir(snippetsDir)
}

tasks.asciidoctor {
    inputs.dir(snippetsDir)
    val test by tasks
    dependsOn(test)
}
