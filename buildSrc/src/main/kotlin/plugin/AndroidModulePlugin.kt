package plugin

import AndroidXDependencies
import AppConfig
import Plugins
import internal.libraryExtension
import javaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

open class AndroidModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.run {
            applyPlugins()
            applyLibraryConfig()
            applyDefaultDependencies()
        }
    }

    private fun Project.applyPlugins() {
        apply(plugin = Plugins.androidLibrary)
        apply(plugin = Plugins.androidKotlin)
        apply(plugin = Plugins.kotlinKapt)
    }

    private fun Project.applyLibraryConfig() {
        libraryExtension.apply {
            compileSdk = AppConfig.compileSdkVersion

            defaultConfig {
                minSdk = AppConfig.minSdkVersion
                targetSdk = AppConfig.targetSdkVersion
                vectorDrawables.useSupportLibrary = true
            }

            testOptions {
                unitTests.all {
                    it.useJUnitPlatform()
                }
            }

            compileOptions {
                sourceCompatibility = javaVersion
                targetCompatibility = javaVersion
            }
        }
    }

    private fun Project.applyDefaultDependencies() {
        AndroidXDependencies.all(dependencies)
    }
}