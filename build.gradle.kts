import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	application
	kotlin("jvm") version "1.4.32"
	kotlin("plugin.spring") version "1.4.32"
	id("org.springframework.boot") version "2.4.5"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("com.justai.jaicf.jaicp-build-plugin") version "0.1.1"
}

group = "com.justai.jaicf"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val jaicf = "1.0.0"
val logback = "1.2.3"

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

application {
	mainClassName = "com.justai.jaicf.game.connections.JaicpServerKt"
}

repositories {
	mavenCentral()
	mavenLocal()
	jcenter()
	maven(uri("https://jitpack.io"))
}

dependencies {
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.boot:spring-boot-starter-web")

	implementation("ch.qos.logback:logback-classic:$logback")
	implementation("com.just-ai.jaicf:core:$jaicf")
	implementation("com.just-ai.jaicf:jaicp:$jaicf")
	implementation("com.just-ai.jaicf:caila:$jaicf")
	implementation("com.just-ai.jaicf:telegram:$jaicf")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
	compileKotlin {
		kotlinOptions.jvmTarget = "1.8"
	}
	compileTestKotlin {
		kotlinOptions.jvmTarget = "1.8"
	}
}

tasks.create("stage") {
	dependsOn("shadowJar")
}

tasks.withType<com.justai.jaicf.plugins.jaicp.build.JaicpBuild> {
	mainClassName.set(application.mainClassName)
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
