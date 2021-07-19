plugins {
    java
    `maven-publish`
    signing
}

group = "io.github.teamcheeze"
version = "1.0.1"

subprojects {
    apply(plugin="java")
    if (this != project(":core")) {
        dependencies {
            compileOnly(project(":core"))
        }
    }
    repositories {
        mavenCentral()
    }
    dependencies {
        implementation("org.jetbrains:annotations:20.1.0")
        implementation("com.google.code.gson:gson:2.8.7")
    }
}
project(":database") {
    dependencies {
        compileOnly("com.google.firebase:firebase-admin:7.3.0")
        compileOnly("org.xerial:sqlite-jdbc:3.36.0.1")
    }
}

tasks {
    create<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        subprojects.forEach {
            from(it.sourceSets["main"].allSource)
            from(it.sourceSets["main"].output)
        }
    }
    create<Jar>("javadocJar") {
        archiveClassifier.set("javadoc")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        subprojects.forEach {
            from(it.tasks.javadoc)
        }
    }
    create<Jar>("databaseJar") {
        archiveClassifier.set("database")
        from(project(":database").sourceSets["main"].allSource)
        from(project(":database").sourceSets["main"].output)
    }
    create<Jar>("coreJar") {
        archiveClassifier.set("core")
        from(project(":core").sourceSets["main"].allSource)
        from(project(":core").sourceSets["main"].output)
    }
    create<Jar>("allJar") {
        archiveClassifier.set("jar-all")
        subprojects.forEach {
            from(it.sourceSets["main"].allSource)
            from(it.sourceSets["main"].output)
        }
    }
    jar {
        subprojects.forEach {
            from(it.sourceSets["main"].allSource)
            from(it.sourceSets["main"].output)
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenPublication") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
            artifact(tasks["databaseJar"])
            artifact(tasks["coreJar"])
            artifact(tasks["allJar"])
            repositories {
                mavenLocal()
                maven {
                    name = "sonatype"
                    credentials.runCatching {
                        val nexusUsername: String by project
                        val nexusPassword: String by project
                        username = nexusUsername
                        password = nexusPassword
                    }.onFailure {
                        logger.warn("Failed to load nexus credentials, Check the gradle.properties")
                    }
                    url = uri(
                        if (version.endsWith("-SNAPSHOT") || version.endsWith(".Beta") || version.endsWith(".beta")) {
                            "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                        } else {
                            "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
                        }
                    )
                }
            }
            pom {
                name.set("jaw")
                description.set("Welcome to the Jaw library!")
                url.set("https://github.com/TeamCheeeze/jaw")
                licenses {
                    license {
                        name.set("The MIT License")
                        url.set("http://www.opensource.org/licenses/mit-license.php")
                    }
                }
                developers {
                    developer {
                        id.set("dolphin2410")
                        name.set("dolphin2410")
                        email.set("teamcheeze@outlook.kr")
                        timezone.set("GMT+9")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/TeamCheeze/jaw.git")
                    developerConnection.set("scm:git:ssh://github.com/TeamCheeze/jaw.git")
                    url.set("https://github.com/TeamCheeze/jaw")
                }
            }
        }
    }
}

signing {
    isRequired = true
    sign(tasks["sourcesJar"], tasks["javadocJar"], tasks["databaseJar"], tasks["coreJar"], tasks["allJar"], project.tasks.jar.get())
    sign(publishing.publications["mavenPublication"])
}