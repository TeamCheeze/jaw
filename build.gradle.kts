plugins {
    java
    `maven-publish`
    signing
    id("org.jetbrains.dokka") version "1.5.0"
}

group = "io.github.dolphin2410"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:20.1.0")
    implementation("com.google.code.gson:gson:2.8.7")
    compileOnly("com.google.firebase:firebase-admin:7.3.0")
    compileOnly("org.xerial:sqlite-jdbc:3.36.0.1")
}

tasks {
    create<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }
    create<Jar>("javadocJar") {
        archiveClassifier.set("javadoc")
        dependsOn("dokkaHtml")
        from("$buildDir/dokka/html/") {
            include("**")
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenPublication") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

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
                description.set("core api library")
                url.set("https://github.com/dolphin2410/jaw")
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
                        email.set("dolphin2410@outlook.kr")
                        timezone.set("GMT+9")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/dolphin2410/jaw.git")
                    developerConnection.set("scm:git:ssh://github.com/dolphin2410/jaw.git")
                    url.set("https://github.com/dolphin2410/jaw")
                }
            }
        }
    }
}

signing {
    isRequired = true
    sign(tasks["sourcesJar"], tasks["javadocJar"])
    sign(publishing.publications["mavenPublication"])
}