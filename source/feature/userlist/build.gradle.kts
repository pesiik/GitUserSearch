plugins {
    id(Plugins.androidModule)
    id(Plugins.kotlinKapt)
    id(Plugins.navigation)
}

dependencies {
    MvvmCore.all(this)
    ViewCore.all(this)
    NetworkDependencies.all(this)
    DIDependencies.all(this)
    AndroidXDependencies.all(this)
}