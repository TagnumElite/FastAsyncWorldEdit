import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
    `eclipse`
    `maven`
}

apply(plugin = "com.github.johnrengelman.shadow")

repositories {
    maven { url = uri("https://hub.spigotmc.org/nexus/content/groups/public") }
    maven { url = uri("https://repo.codemc.org/repository/maven-public") }
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
    mavenLocal()
}

dependencies {
    "compile"(project(":worldedit-bukkit"))
    "compile"("com.martiansoftware:jsap:2.1")
}
tasks.named<Copy>("processResources") {
    filesMatching("plugin.yml") {
        expand("name" to (project.parent?.name ?: "FAVS"))
        expand("version" to (project.parent?.version ?: "UNKNOWN"))
    }
}

tasks.named<ShadowJar>("shadowJar") {
    dependencies {
        include(dependency("com.martiansoftware:jsap:2.1"))
    }
}

tasks.named("assemble").configure {
    dependsOn("shadowJar")
}
