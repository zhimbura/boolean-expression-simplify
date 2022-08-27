plugins {
    kotlin("multiplatform") version "1.7.10"
    id("org.jetbrains.dokka") version "1.5.0"
    id("org.jetbrains.kotlinx.kover") version "0.5.0"
}

group = "org.boolean"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

/**-------------------- kover --------------------*/
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlinx:kover:0.5.0-RC2")
    }
}
apply(plugin = "kover")
kover {
    coverageEngine.set(kotlinx.kover.api.CoverageEngine.JACOCO)
    intellijEngineVersion.set("1.0.614")
    jacocoEngineVersion.set("0.8.7")
}

/**-------------------- dokka --------------------*/
apply(plugin = "org.jetbrains.dokka")


kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "16"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
        val nativeMain by getting
        val nativeTest by getting
    }
}
