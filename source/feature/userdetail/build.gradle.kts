plugins {
    id(Plugins.androidModule)
    id(Plugins.kotlinKapt)
}

dependencies {
    MvvmCore.all(this)
    NetworkDependencies.all(this)
    DIDependencies.all(this)
}