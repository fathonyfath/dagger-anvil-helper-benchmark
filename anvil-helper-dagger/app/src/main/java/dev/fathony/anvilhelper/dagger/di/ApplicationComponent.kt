package dev.fathony.anvilhelper.dagger.di

import dagger.BindsInstance
import dagger.Component
import dev.fathony.anvilhelper.base.dagger.DaggerComponent
import dev.fathony.anvilhelper.common.CommonModule
import dev.fathony.anvilhelper.dagger.DaggerApplication
import dev.fathony.anvilhelper.dagger.MainActivity
import javax.inject.Singleton

//region Importing activities injection definition

//endregion

@Singleton
@Component(
    modules = [
        CommonModule::class,
        ApplicationModule::class,
        SubprojectModule::class
    ]
)
abstract class ApplicationComponent : DaggerComponent<DaggerApplication>,
//region Implement interfaces
    MainActivityComponentFactory
//endregion
{
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: DaggerApplication): ApplicationComponent
    }

    abstract fun mainActivityComponentFactory(): MainActivityComponent.Factory

    override fun createMainActivityComponent(activity: MainActivity): MainActivityComponent =
        mainActivityComponentFactory().create(activity)

    //region Activities registration
    
    //endregion
}
