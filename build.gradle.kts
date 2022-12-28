import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
plugins {
    id("org.springframework.boot") version "2.7.6"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    id("jacoco")
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}
group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.6")
    //For Oauth2 Setting
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client:2.7.6")

    testImplementation("org.springframework.restdocs:spring-restdocs-asciidoctor:2.0.6.RELEASE")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:2.0.6.RELEASE")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest:kotest-runner-junit5:5.5.2")
    testImplementation("io.kotest:kotest-assertions-core:5.5.2")
    testImplementation("io.kotest:kotest-property:5.5.2")
    testImplementation("io.mockk:mockk:1.13.2")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jacoco{
    toolVersion="0.8.8"
}
fun ConfigurableFileCollection.excludeSpringBootApplicationClass(){
    setFrom(
        files(files.map {
            fileTree(it) {
                exclude("com/example/springkotlintemplate/SpringKotlinTemplateApplication*")
            }
        })
    )
}
tasks.jacocoTestReport {
    reports {
        html.required.set(true)
        xml.required.set(true)
        csv.required.set(false)
    }
    classDirectories.excludeSpringBootApplicationClass()
}
tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            classDirectories.excludeSpringBootApplicationClass()
            element = "CLASS"

            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = "0.90".toBigDecimal()
            }

            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.80".toBigDecimal()
            }

            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "200".toBigDecimal()
            }
        }
    }
}
//Spring Rest Docs
tasks {
    val snippetsDir = file("$buildDir/generated-snippets")
    mkdir("$buildDir/generated-snippets")

    clean {
        delete("src/main/resources/static/docs")
    }

    test {
        useJUnitPlatform()
        systemProperty("org.springframework.restdocs.outputDir", snippetsDir)
        outputs.dir(snippetsDir)
    }

    build {
        dependsOn("copyDocument")
    }

    asciidoctor {
        dependsOn(test)

        attributes(
            mapOf("snippets" to snippetsDir)
        )
        inputs.dir(snippetsDir)

        doFirst {
            delete("src/main/resources/static/docs")
        }
    }

    register<Copy>("copyDocument") {
        dependsOn(asciidoctor)

        destinationDir = file(".")
        from(asciidoctor.get().outputDir) {
            duplicatesStrategy=DuplicatesStrategy.INCLUDE
            into("src/main/resources/static/docs")
        }
    }

    bootJar {
        dependsOn(asciidoctor)

        from(asciidoctor.get().outputDir) {
            duplicatesStrategy=DuplicatesStrategy.INCLUDE
            into("BOOT-INF/classes/static/docs")
        }
    }
}
