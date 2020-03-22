import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

group = "gov.cdmx.twitter.sentiment"
version = "1.0-SNAPSHOT"

tasks {
    named<ShadowJar>("shadowJar") {
        manifest {
            attributes(mapOf("Main-Class" to "analytics.socialnetworks.twitter.Main"))
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}
