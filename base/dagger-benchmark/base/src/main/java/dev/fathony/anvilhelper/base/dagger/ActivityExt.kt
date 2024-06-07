package dev.fathony.anvilhelper.base.dagger

import android.app.Activity
import kotlin.properties.ReadOnlyProperty

inline fun <reified F : DaggerSubcomponentFactory, T : Activity> Activity.applicationComponent(
    noinline create: (F) -> DaggerComponent<T>,
): ReadOnlyProperty<T, DaggerComponent<T>> = createComponentDelegate(
    factoryClass = F::class,
    parentProducer = { this@applicationComponent.application },
    create = create,
)
