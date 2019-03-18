import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val vertxVersion = "3.6.3"
val spek_version = "2.0.0-rc.1"
val kotlin_version = "1.2.61"

plugins {
    base
    java
    application
    kotlin("jvm") version "1.2.61"
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("org.slf4j:slf4j-api:1.7.14")
    implementation("ch.qos.logback:logback-classic:1.1.3")
    testImplementation("io.mockk:mockk:1.8.7")
    testImplementation("org.assertj:assertj-core:3.11.1")
    testImplementation ("org.spekframework.spek2:spek-dsl-jvm:${spek_version}")  {
        exclude(group = "org.jetbrains.kotlin")
    }
    testRuntimeOnly ("org.spekframework.spek2:spek-runner-junit5:${spek_version}") {
        exclude(group = "org.jetbrains.kotlin")
        //exclude(group = "org.junit.platform")
    }
    // spek requires kotlin-reflect, can be omitted if already in the classpath
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")
    "io.vertx:vertx".let { v ->
        compile("$v-core:$vertxVersion")
        compileOnly("$v-lang-kotlin:$vertxVersion")
        compileOnly("$v-lang-kotlin-coroutines:$vertxVersion")
        compile("$v-web-api-contract:$vertxVersion")
        compileOnly("$v-health-check:$vertxVersion")
    }
    implementation("org.apache.poi:poi:3.17")
    implementation("org.apache.poi:poi-ooxml:3.17")
}

