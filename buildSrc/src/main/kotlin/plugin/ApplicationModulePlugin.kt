package plugin

import AndroidXDependencies
import AppConfig
import DIDependencies
import KotlinDependencies
import NetworkDependencies
import Plugins
import ext.featureModulesDirectory
import ext.isGradleProjectDir
import internal.applicationExtension
import internal.implementation
import java.io.File
import javaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.project

class ApplicationModulePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            applyPlugins()
            applyApplicationConfig()
            applyDependencies()
        }
    }

    private fun Project.applyPlugins() {
        plugins.run {
            apply(plugin = Plugins.androidApplication)
            apply(plugin = Plugins.kotlinModule)
            apply(plugin = Plugins.dependenciesVersions)
            apply(plugin = Plugins.kotlinKapt)
        }
    }

    private fun Project.applyApplicationConfig() {
        applicationExtension.apply {
            compileSdk = AppConfig.compileSdkVersion
            buildToolsVersion = AppConfig.buildToolsVersion

            defaultConfig {
                applicationId = AppConfig.applicationId
                minSdk = AppConfig.minSdkVersion
                targetSdk = AppConfig.targetSdkVersion
                versionCode = AppConfig.codeVersion
                versionName = AppConfig.versionName
            }

            compileOptions {
                sourceCompatibility = javaVersion
                targetCompatibility = javaVersion
            }
        }
    }

    private fun Project.applyDependencies() {
        dependencies.apply {
            // Feature modules
            File(featureModulesDirectory).listFiles()?.forEach { featureModule ->
                if (featureModule.isDirectory && featureModule.isGradleProjectDir) {
                    implementation(project(":${featureModule.name}"))
                }
            }

            // Libraries
            implementation(KotlinDependencies.stdLib)
            AndroidXDependencies.all(this)
            NetworkDependencies.all(this)
            DIDependencies.all(this)
        }
    }
}