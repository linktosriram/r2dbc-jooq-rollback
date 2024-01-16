import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.21"
	kotlin("plugin.spring") version "1.9.21"

	id("dev.monosoul.jooq-docker") version "5.0.8"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")

	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.flywaydb:flyway-core")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.springframework:spring-jdbc")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("org.postgresql:r2dbc-postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")

	jooqCodegen("org.postgresql:postgresql")
	jooqCodegen("org.flywaydb:flyway-core")
	jooqCodegen("org.jooq:jooq-codegen")

	implementation("org.jooq:jooq-kotlin-coroutines:3.18.7")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.generateJooqClasses {
	schemas.set(listOf("demo-schema"))
	basePackageName.set("com.example.demo.generated")
	migrationLocations.setFromFilesystem(project.files("src/main/resources/db/migration"))
	outputDirectory.set(project.layout.buildDirectory.dir("generated-jooq"))
	includeFlywayTable.set(false)
	outputSchemaToDefault.set(setOf("demo-schema"))

	usingJavaConfig {
		name = "org.jooq.codegen.KotlinGenerator"
	}
}