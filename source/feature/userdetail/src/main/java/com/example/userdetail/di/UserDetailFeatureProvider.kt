package com.example.userdetail.di

interface UserDetailFeatureProvider {

    fun provideUserDetailComponentBuilder(): UserDetailComponent.Builder
}