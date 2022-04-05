package ext

import org.gradle.api.Project

val Project.featureModulesDirectory
    get() = "$rootDir/source/feature"