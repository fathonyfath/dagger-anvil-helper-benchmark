package dev.fathony.anvilhelper.dagger.di

import dev.fathony.anvilhelper.base.dagger.DaggerSubcomponentFactory
import dev.fathony.anvilhelper.dagger.MainActivity

interface MainActivityComponentFactory : DaggerSubcomponentFactory {
    fun createMainActivityComponent(activity: MainActivity): MainActivityComponent
}
