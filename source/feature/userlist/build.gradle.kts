import Core.applyCore

plugins {
    id(Plugins.androidModule)
    id(Plugins.kotlinKapt)
}

dependencies {
    applyCore()
    NetworkDependencies.all(this)
    DIDependencies.all(this)
}