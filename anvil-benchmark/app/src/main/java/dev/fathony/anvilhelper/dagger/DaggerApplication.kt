package dev.fathony.anvilhelper.dagger

import android.app.Application
import dev.fathony.anvil.helper.api.DispatchingInjector
import dev.fathony.anvil.helper.api.HasInjector
import dev.fathony.anvilhelper.dagger.di.ApplicationComponent
import dev.fathony.anvilhelper.dagger.di.DaggerApplicationComponent
import javax.inject.Inject

class DaggerApplication : Application(), HasInjector {

    private lateinit var _component: ApplicationComponent
    
    @Inject
    lateinit var dispatchingInjector: DispatchingInjector<Any>

    override fun onCreate() {
        super.onCreate()
        _component = DaggerApplicationComponent.factory().create(this)
        _component.inject(this)
    }

    override fun injector(): DispatchingInjector<Any> = dispatchingInjector
}
