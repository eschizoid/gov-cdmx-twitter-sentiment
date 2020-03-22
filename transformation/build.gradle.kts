import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

group = "analytics.socialnetworks.twitter"
version = "1.0-SNAPSHOT"


tasks {
    named<ShadowJar>("shadowJar") {
        manifest {
            attributes(mapOf("Main-Class" to "analytics.socialnetworks.twitter.Transformation"))
        }
        dependencies {
            exclude("*.csv")
        }
    }
}
