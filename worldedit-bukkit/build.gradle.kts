import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
}

applyPlatformAndCoreConfiguration()
applyShadowConfiguration()

repositories {
    maven { url = uri("https://hub.spigotmc.org/nexus/content/groups/public") }
    maven { url = uri("https://repo.codemc.org/repository/maven-public") }
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
}

configurations.all {
    resolutionStrategy {
        force("com.google.guava:guava:21.0")
    }
}

dependencies {
    "compile"("net.milkbowl.vault:VaultAPI:1.7")
    "api"(project(":worldedit-core"))
    "api"(project(":worldedit-libs:core")) // TODO remove once core can compile
    "api"(project(":worldedit-libs:bukkit"))
    "api"("org.bukkit:bukkit:1.13.2-R0.1-SNAPSHOT") {
        exclude("junit", "junit")
    }
    "compileOnly"("com.destroystokyo.paper:paper-api:1.14.4-R0.1-SNAPSHOT")
    "implementation"("io.papermc:paperlib:1.0.2")
    "compileOnly"("com.sk89q:dummypermscompat:1.10")
    "compileOnly"("org.spigotmc:spigot:1.13.2-R0.1-SNAPSHOT")
    "compileOnly"("BuildTools:lastSuccessfulBuild:spigot-1.14.4@jar")
    "implementation"("org.apache.logging.log4j:log4j-slf4j-impl:2.8.1")
    "implementation"("org.bstats:bstats-bukkit:1.5")
    "implementation"("com.sk89q.worldguard:worldguard-core:7.0.0-20190215.210421-39")
    "implementation"("com.sk89q.worldguard:worldguard-legacy:7.0.0-20190215.210421-39")
    "implementation"("com.massivecraft:factions:2.8.0")
    "implementation"("com.drtshock:factions:1.6.9.5")
    "implementation"("com.factionsone:FactionsOne:1.2.2")
    "implementation"("me.ryanhamshire:GriefPrevention:11.5.2")
    "implementation"("com.massivecraft:mcore:7.0.1")
    "implementation"("net.sacredlabyrinth.Phaed:PreciousStones:10.0.4-SNAPSHOT")
    "implementation"("net.jzx7:regios:5.9.9")
    "implementation"("com.bekvon.bukkit.residence:Residence:4.5._13.1")
    "implementation"("com.palmergames.bukkit:towny:0.84.0.9")
    "implementation"("com.thevoxelbox.voxelsniper:voxelsniper:5.171.0")
    "implementation"("com.comphenix.protocol:ProtocolLib-API:4.4.0-SNAPSHOT")
    "implementation"("com.wasteofplastic:askyblock:3.0.8.2")
    "testCompile"("org.mockito:mockito-core:1.9.0-rc1")
}

tasks.named<Copy>("processResources") {
    filesMatching("plugin.yml") {
        expand("internalVersion" to project.ext["internalVersion"])
    }
//    from(zipTree("src/main/resources/worldedit-adapters.jar").matching {
//        exclude("META-INF/")
//    })
//    exclude("**/worldedit-adapters.jar")
}

//jar.archiveName="fawe-bukkit-${project.parent.version}.jar";
//jar.destinationDir = file '../mvn/com/boydti/fawe-bukkit/' + project.parent.version;
tasks.named<Jar>("jar") {
    manifest {
        attributes("Class-Path" to "truezip.jar WorldEdit/truezip.jar js.jar WorldEdit/js.jar",
                "WorldEdit-Version" to project.version)
    }
}

tasks.named<ShadowJar>("shadowJar") {
    dependencies {
        relocate("org.slf4j", "com.sk89q.worldedit.slf4j")
        relocate("org.apache.logging.slf4j", "com.sk89q.worldedit.log4jbridge")
        include(dependency(":worldedit-core"))
        include(dependency("org.slf4j:slf4j-api"))
        include(dependency("org.apache.logging.log4j:log4j-slf4j-impl"))
        relocate("org.bstats", "com.sk89q.worldedit.bukkit.bstats") {
            include(dependency("org.bstats:bstats-bukkit:1.5"))
        }
        relocate("io.papermc.lib", "com.sk89q.worldedit.bukkit.paperlib") {
            include(dependency("io.papermc:paperlib:1.0.2"))
        }
    }
}

tasks.named("assemble").configure {
    dependsOn("shadowJar")
}
