package dev.fathony.anvilhelper.base.dagger

import android.app.Activity
import dev.fathony.anvil.helper.api.HasInjector

object AndroidInjection {

    fun inject(activity: Activity) {
        val application = activity.application
        if (application !is HasInjector) {
            throw RuntimeException("${application.javaClass.canonicalName} does not implement ${HasInjector::class.java.canonicalName}")
        }
        
        inject(activity, application)
    }
    
    private fun inject(any: Any, hasInjector: HasInjector) {
        val dispatchingInjector = hasInjector.injector()
        dispatchingInjector.inject(any)
    }
}
