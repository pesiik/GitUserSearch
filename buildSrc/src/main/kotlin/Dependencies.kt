@file:Suppress("MemberVisibilityCanBePrivate")

import internal.implementation
import internal.kapt
import internal.testImplementation
import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

val javaVersion = JavaVersion.VERSION_11

object MvvmCore {

    const val mvvmCoreModule = ":mvvmcore"

    fun all(dependencies: DependencyHandler) = dependencies.apply {
        implementation(project(mvvmCoreModule))
    }
}

object ViewCore {

    const val viewCoreModule = ":viewcore"

    fun all(dependencies: DependencyHandler) = dependencies.apply {
        implementation(project(viewCoreModule))
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
        const val lifecycleVersion = "2.4.1"
        const val navigation = "2.4.1"
        const val recyclerView = "1.2.1"
    }

    const val coreKtx = "androidx.core:core-ktx:${Versions.core}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val lifecycle = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"

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


object TestDependencies {
    object Versions {
        const val junit5Version = "5.3.2"
        const val mockkVersion = "1.10.6"
    }

    const val jUnit = "org.junit.jupiter:junit-jupiter-api:${Versions.junit5Version}"
    const val jUnitEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit5Version}"
    const val jUnitParams = "org.junit.jupiter:junit-jupiter-params:${Versions.junit5Version}"
    const val mockk = "io.mockk:mockk:${Versions.mockkVersion}"

    fun all(dependencies: DependencyHandler) = dependencies.apply {
        testImplementation(jUnit)
        testImplementation(jUnitEngine)
        testImplementation(jUnitParams)
        testImplementation(mockk)
    }
}

object NetworkDependencies {
    object Versions {
        const val okhttpVersion = "4.9.0"
        const val retrofitVersion = "2.9.0"
    }

    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttpVersion}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpVersion}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"

    fun all(dependencies: DependencyHandler) = dependencies.apply {
        implementation(okhttp)
        implementation(loggingInterceptor)
        implementation(retrofit)
        implementation(retrofitGsonConverter)
    }
}

object DIDependencies {
    object Versions {
        const val daggerVersion = "2.41"
    }

    const val dagger = "com.google.dagger:dagger:${Versions.daggerVersion}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.daggerVersion}"

    fun all(dependencies: DependencyHandler) = dependencies.apply {
        implementation(dagger)
        kapt(daggerCompiler)
    }
}

object Images {
    object Versions {
        const val picassoVersion = "2.71828"
        const val paletteVersion = "28.0.0"
    }

    private const val picasso = "com.squareup.picasso:picasso:${Versions.picassoVersion}"
    private const val palette = "com.android.support:palette-v7:${Versions.paletteVersion}"

    fun all(dependencies: DependencyHandler) = dependencies.apply {
        implementation(picasso)
        implementation(palette)
    }
}