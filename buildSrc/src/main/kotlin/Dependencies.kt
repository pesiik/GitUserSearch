@file:Suppress("MemberVisibilityCanBePrivate")

import AndroidTest.Versions.junitVersion
import Core.Versions.buildToolsVersion
import Core.Versions.coreKTXVersion
import Core.Versions.kotlinVersion
import internal.implementation
import internal.testImplementation
import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.dsl.DependencyHandler

val javaVersion = JavaVersion.VERSION_11

object Core {

    object Versions {
        const val coreKTXVersion = "1.7.0"
        const val kotlinVersion = "1.6.10"
        const val buildToolsVersion = "7.1.1"
    }

    private const val coreKtx = "androidx.core:core-ktx:$coreKTXVersion"
    private const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    private const val androidGradlePlugin = "com.android.tools.build:gradle:${buildToolsVersion}"

    fun DependencyHandler.applyCore() = apply {
        implementation(coreKtx)
        implementation(kotlinGradlePlugin)
    }

    fun DependencyHandler.applyAndroidGradlePlugin() = apply {
        implementation(androidGradlePlugin)
    }
}

object Plugins {
    const val applicationModule = "application-module"
    const val androidModule = "android-module"
    const val kotlinModule = "kotlin-module"

    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val androidKotlin = "kotlin-android"

    const val javaLibrary = "java-library"
    const val kotlin = "kotlin"
    const val kotlinKapt = "kotlin-kapt"
    const val kotlinJVM = "org.jetbrains.kotlin.jvm"

    const val navigation = "androidx.navigation.safeargs.kotlin"
    const val dependenciesVersions = "com.github.ben-manes.versions"

    const val ktlint = "org.jlleitschuh.gradle.ktlint"
    const val detekt = "io.gitlab.arturbosch.detekt"
}

object Sdk {
    object Versions {
        const val versionCode = 1
    }

    const val applicationId = "com.example.gitusersearch"
    const val versionName = "1.0"
    const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object KotlinDependencies {
    object Versions {
        const val kotlinVersion = "1.6.10"
        const val kotlinCoroutines = "1.6.0"
    }

    const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlinVersion}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutines}"

    fun DependencyHandler.defaultModuleLibs() = apply {
        implementation(stdLib)
    }

    fun DependencyHandler.coroutines() = apply {
        implementation(coroutinesCore)
        implementation(coroutinesAndroid)
        implementation(coroutinesTest)
    }

    fun all(dependencies: DependencyHandler) = dependencies.apply {
        defaultModuleLibs()
        coroutines()
    }
}

object AndroidXDependencies {
    object Versions {
        const val appCompat = "1.4.1"
        const val core = "1.7.0"
        const val material = "1.5.0"
        const val constraintLayout = "2.1.3"
        const val lifecycle = "2.4.1"
        const val navigation = "2.4.1"
        const val recyclerView = "1.2.1"
    }

    const val coreKtx = "androidx.core:core-ktx:${Versions.core}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val lifecycle = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"

    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    fun DependencyHandler.core() = apply {
        implementation(coreKtx)
    }

    fun DependencyHandler.ui() = apply {
        core()
        implementation(appCompat)
        implementation(material)
        implementation(recyclerView)
        implementation(constraintLayout)
    }

    fun DependencyHandler.navigation() = apply {
        implementation(navigationFragment)
        implementation(navigationUi)
    }

    fun all(dependencies: DependencyHandler) = dependencies.apply {
        ui()
        navigation()
        implementation(lifecycle)
    }
}

object AndroidTest {
    object Versions {
        const val junitVersion = "4.13.2"
    }

    private const val junit = "junit:junit:$junitVersion"

    fun DependencyHandler.applyAndroidTest() = apply {
        testImplementation(junit)
    }
}