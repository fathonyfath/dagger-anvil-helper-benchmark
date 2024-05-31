package dev.fathony.anvilhelper.dagger

import android.app.Application
import dev.fathony.anvilhelper.dagger.di.ApplicationComponent
import dev.fathony.anvilhelper.dagger.di.DaggerApplicationComponent

class DaggerApplication : Application() {

    private lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.factory().create(this)
    }
}
