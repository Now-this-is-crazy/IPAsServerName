plugins {
    id("dev.kikugie.loom-back-compat")
    `maven-publish`
}

version = "${property("mod.version")}+${sc.current.version}"
base.archivesName = property("mod.id") as String

val requiredJava: JavaVersion = when {
    sc.current.parsed >= "26.1" -> JavaVersion.VERSION_25
    sc.current.parsed >= "1.20.5" -> JavaVersion.VERSION_21
    sc.current.parsed >= "1.18" -> JavaVersion.VERSION_17
    sc.current.parsed >= "1.17" -> JavaVersion.VERSION_16
    else -> JavaVersion.VERSION_1_8
}

dependencies {
    minecraft("com.mojang:minecraft:${sc.current.version}")
    loomx.applyMojangMappings()
    modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
}

loom {
    fabricModJsonPath = rootProject.file("src/main/resources/fabric.mod.json")

    runConfigs.all {
        preferGradleTask = true
        generateRunConfig = true
        runDirectory = rootProject.file("run")
    }
}

java {
    withSourcesJar()
    sourceCompatibility = requiredJava
    targetCompatibility = requiredJava

    toolchain {
        languageVersion = JavaLanguageVersion.of(requiredJava.majorVersion)
    }
}

tasks {
    processResources {
        val resourceProperties = mapOf(
            "version" to sc.properties["mod.version"],
            "minecraft" to sc.properties["mod.mc_compat"],
            "java" to requiredJava.majorVersion
        )

        inputs.properties(resourceProperties)

        filesMatching("fabric.mod.json") {
            expand(resourceProperties)
        }

        val mixinCompatibility = "JAVA_${requiredJava.majorVersion}"
        filesMatching("*.mixins.json") {
            expand("java" to mixinCompatibility)
        }
    }

    withType<JavaCompile>().configureEach {
        if (requiredJava >= JavaVersion.VERSION_1_9) {
            options.release = requiredJava.majorVersion.toInt()
        }
    }

    withType<Jar> {
        val modId = project.property("mod.id")
        inputs.property("modId", modId)

        from(rootProject.file("LICENSE")) {
            rename { "$it-$modId" }
        }
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds mod version jars."

        inputs.property("version", project.property("mod.version"))
        from(loomx.modJar.flatMap { it.archiveFile }, loomx.modSourcesJar.flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory.dir("libs/${project.property("mod.version")}"))
    }
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
