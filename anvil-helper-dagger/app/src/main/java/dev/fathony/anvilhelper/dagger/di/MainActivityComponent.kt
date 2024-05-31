package dev.fathony.anvilhelper.dagger.di

import dagger.BindsInstance
import dagger.Subcomponent
import dev.fathony.anvilhelper.base.dagger.DaggerComponent
import dev.fathony.anvilhelper.base.dagger.DaggerSubcomponentFactory
import dev.fathony.anvilhelper.base.dagger.scope.ActivityScope
import dev.fathony.anvilhelper.dagger.MainActivity

@ActivityScope
@Subcomponent
interface MainActivityComponent : DaggerComponent<MainActivity> {
    @Subcomponent.Factory
    interface Factory : DaggerSubcomponentFactory {
        fun create(@BindsInstance target: MainActivity): MainActivityComponent
    }
}
