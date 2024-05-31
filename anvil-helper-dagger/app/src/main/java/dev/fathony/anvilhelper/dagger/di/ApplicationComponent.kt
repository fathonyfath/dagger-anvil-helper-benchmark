package dev.fathony.anvilhelper.dagger.di

import dagger.BindsInstance
import dagger.Component
import dev.fathony.anvilhelper.common.CommonModule
import dev.fathony.anvilhelper.dagger.DaggerApplication
import javax.inject.Singleton

//region Importing Activities definition

//endregion

@Singleton
@Component(
    modules = [
        CommonModule::class
    ]
)
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: DaggerApplication): ApplicationComponent
    }

    //region Activities registration

    //endregion
}
