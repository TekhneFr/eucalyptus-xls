val vertxVersion = "3.5.4"
val junitVersion = "5.3.0"

plugins {
    base
    java
    application
    kotlin("jvm") version "1.2.61"
}

group = "com.tekhne.eucalyptus"


repositories {
    mavenCentral()
}

application {
    version = "1.0.0"
    applicationName = "eucalyptus-xls"
    mainClassName = "io.vertx.core.Launcher"
}

dependencies {
    compileOnly(kotlin("stdlib"))
    implementation("org.slf4j:slf4j-api:1.7.14")
    implementation("ch.qos.logback:logback-classic:1.1.3")
    testImplementation("io.mockk:mockk:1.8.7")
    testImplementation("org.assertj:assertj-core:3.11.1")
    "org.junit.jupiter".let {v ->
        testImplementation("$v:junit-jupiter-api:$junitVersion")
        testImplementation("$v:junit-jupiter-params:$junitVersion")
        testRuntimeOnly("$v:junit-jupiter-engine:$junitVersion")
    }
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

