plugins {
    id(Plugins.androidModule)
    id(Plugins.kotlinKapt)
}

dependencies {
    Core.all(this)
    NetworkDependencies.all(this)
    DIDependencies.all(this)
}