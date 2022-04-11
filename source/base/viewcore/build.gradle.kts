plugins {
    id(Plugins.androidLibrary)
    id(Plugins.androidKotlin)
    id(Plugins.kotlinKapt)
}

android {
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        minSdk = AppConfig.minSdkVersion
    }

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    kotlinOptions {
        jvmTarget = AppConfig.jvmTargetVersion
    }
}

dependencies {
    Images.all(this)
    AndroidXDependencies.all(this)
    DIDependencies.all(this)
}