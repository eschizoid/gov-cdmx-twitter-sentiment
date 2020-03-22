plugins {
    id("idea")
    id("cz.alenkacz.gradle.scalafmt") version "1.8.0"
    id("com.github.johnrengelman.shadow") version "5.0.0"
    id("com.github.maiflai.scalatest") version "0.25"
    kotlin("jvm") version "1.3.70"
}

allprojects {
    repositories {
        jcenter()
        maven(url = "http://dl.bintray.com/spark-packages/maven")
        maven(url = "http://repo.artima.com/releases")
    }
}

val AwsJavaSdk = "1.7.4"
val HadoopAws = "2.7.3"
val Log4jVersion = "2.13.1"
val ScalaVersion = "2.11"
val ScalaMinorVersion = "2.11.8"
val SparkDariaVersion = "0.31.0-s_2.11"
val SparkVersion = "2.4.3"

subprojects {
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "cz.alenkacz.gradle.scalafmt")
    apply(plugin = "com.github.maiflai.scalatest")
    apply(plugin = "java")
    apply(plugin = "scala")

    dependencies {
        // Scala
        compileOnly("org.scala-lang:scala-library:$ScalaMinorVersion")
        compileOnly("org.scala-lang:scala-reflect:$ScalaMinorVersion")
        compileOnly("org.scala-lang:scala-compiler:$ScalaMinorVersion")

        // AWS
        implementation("com.amazonaws:aws-java-sdk:$AwsJavaSdk")
        implementation("org.apache.hadoop:hadoop-aws:$HadoopAws")

        // Spark
        compileOnly("org.apache.spark:spark-core_$ScalaVersion:$SparkVersion")
        compileOnly("org.apache.spark:spark-mllib_$ScalaVersion:$SparkVersion")
        compileOnly("org.apache.spark:spark-streaming_$ScalaVersion:$SparkVersion")
        compileOnly("org.apache.spark:spark-sql_$ScalaVersion:$SparkVersion")
        implementation("org.apache.bahir:spark-streaming-twitter_$ScalaVersion:2.4.0")
        implementation("mrpowers:spark-daria:$SparkDariaVersion")

        // Log4j
        implementation("org.apache.logging.log4j:log4j-api:$Log4jVersion")
        implementation("org.apache.logging.log4j:log4j-core:$Log4jVersion")

        testImplementation("org.apache.spark:spark-core_$ScalaVersion:$SparkVersion")
        testImplementation("org.apache.spark:spark-mllib_$ScalaVersion:$SparkVersion")
        testImplementation("org.apache.spark:spark-streaming_$ScalaVersion:$SparkVersion")
        testImplementation("org.apache.spark:spark-sql_$ScalaVersion:$SparkVersion")
        testImplementation("org.scalatest:scalatest_$ScalaVersion:3.0.6")
        testImplementation("org.scalamock:scalamock_$ScalaVersion:4.1.0")
        testImplementation("org.mockito:mockito-core:2.18.3")
        testImplementation("org.pegdown:pegdown:1.6.0")
    }

    tasks {
        build {
            dependsOn(shadowJar)
        }
    }

    scalafmt {
        configFilePath = ".scalafmt.conf"
    }
}

