package dev.fathony.anvilhelper.dagger

import android.app.Application
import dev.fathony.anvilhelper.base.dagger.DaggerComponent
import dev.fathony.anvilhelper.base.dagger.DaggerComponentOwner
import dev.fathony.anvilhelper.dagger.di.ApplicationComponent
import dev.fathony.anvilhelper.dagger.di.DaggerApplicationComponent

class DaggerApplication : Application(), DaggerComponentOwner {

    private lateinit var _component: ApplicationComponent

    override val component: DaggerComponent<*>
        get() = _component

    override fun onCreate() {
        super.onCreate()
        _component = DaggerApplicationComponent.factory().create(this)
    }
}
