plugins {
    id("java-library")
    id("net.ltgt.apt-eclipse")
    id("net.ltgt.apt-idea")
}

applyPlatformAndCoreConfiguration()

configurations.all {
    resolutionStrategy {
        force("com.google.guava:guava:21.0")
    }
}

dependencies {
    "compile"(project(":worldedit-libs:core"))
    "compile"("de.schlichtherle:truezip:6.8.3")
    "compile"("org.mozilla:rhino:1.7.11")
    "compile"("org.yaml:snakeyaml:1.23")
    "compile"("com.google.guava:guava:21.0")
    "compile"("com.google.code.findbugs:jsr305:1.3.9")
    "compile"("com.google.code.gson:gson:2.8.0")
    "compile"("com.googlecode.json-simple:json-simple:1.1.1")
    "compile"("org.slf4j:slf4j-api:1.7.26")
    "compile"("com.thoughtworks.paranamer:paranamer:2.6")

    "compileOnly"(project(":worldedit-libs:core:ap"))
    "annotationProcessor"(project(":worldedit-libs:core:ap"))
    // ensure this is on the classpath for the AP
    "annotationProcessor"("com.google.guava:guava:21.0")
    "compileOnly"("com.google.auto.value:auto-value-annotations:${Versions.AUTO_VALUE}")
    "annotationProcessor"("com.google.auto.value:auto-value:${Versions.AUTO_VALUE}")
    "testCompile"("org.mockito:mockito-core:1.9.0-rc1")
    //FAWE Depends
    "compileOnly"("net.fabiozumbi12:redprotect:1.9.6")
    "compileOnly"("com.github.intellectualsites.plotsquared:PlotSquared-API:latest")
    "compile"("com.mojang:datafixerupper:1.0.20")
    "compile"("com.github.luben:zstd-jni:1.1.1")
    "compile"("co.aikar:fastutil-lite:1.0")
}

tasks.withType<JavaCompile>().configureEach {
    dependsOn(":worldedit-libs:build")
    options.compilerArgs.add("-Aarg.name.key.prefix=")
}

sourceSets {
    main {
        java {
            srcDir("src/main/java")
            srcDir("src/legacy/java")
        }
        resources {
            srcDir("src/main/resources")
        }
    }
}
