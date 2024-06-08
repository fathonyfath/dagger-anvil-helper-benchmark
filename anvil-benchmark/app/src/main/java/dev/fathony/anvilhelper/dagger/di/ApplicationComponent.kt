package dev.fathony.anvilhelper.dagger.di

import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component
import dev.fathony.anvilhelper.base.anvil.ApplicationScope
import dev.fathony.anvilhelper.dagger.DaggerApplication
import javax.inject.Singleton

@Singleton
@MergeComponent(
    scope = ApplicationScope::class,
    modules = [
        ApplicationModule::class,
    ]
)
interface ApplicationComponent {

    fun inject(application: DaggerApplication)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: DaggerApplication): ApplicationComponent
    }
}
