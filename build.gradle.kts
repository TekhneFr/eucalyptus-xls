import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val vertxVersion = "3.7.1"
val spekVersion = "2.0.0-rc.1"
val kotlinVersion = "1.2.61"

plugins {
    base
    java
    application
    kotlin("jvm") version "1.3.40"
}

group = "com.tekhne.eucalyptus"

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform {
        includeEngines("spek2")
    }
}

repositories {
    jcenter()
    mavenCentral()
}

application {
    version = "1.0.0"
    applicationName = "eucalyptus-xls"
    mainClassName = "io.vertx.core.Launcher"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.slf4j:slf4j-api:1.7.14")
    implementation("ch.qos.logback:logback-classic:1.1.3")
    testImplementation("io.mockk:mockk:1.8.7")
    testImplementation("org.assertj:assertj-core:3.11.1")
    testImplementation ("org.spekframework.spek2:spek-dsl-jvm:${spekVersion}")  {
        exclude(group = "org.jetbrains.kotlin")
    }
    testRuntimeOnly ("org.spekframework.spek2:spek-runner-junit5:${spekVersion}") {
        exclude(group = "org.jetbrains.kotlin")
        //exclude(group = "org.junit.platform")
    }
    // spek requires kotlin-reflect, can be omitted if already in the classpath
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    "io.vertx:vertx".let { v ->
        compile("$v-core:$vertxVersion")
        compileOnly("$v-lang-kotlin:$vertxVersion")
        compileOnly("$v-lang-kotlin-coroutines:$vertxVersion")
        compile("$v-web-api-contract:$vertxVersion")
        compileOnly("$v-health-check:$vertxVersion")
        compile("$v-mongo-client:$vertxVersion")
    }
    implementation("org.apache.poi:poi:3.17")
    implementation("org.apache.poi:poi-ooxml:3.17")
}

