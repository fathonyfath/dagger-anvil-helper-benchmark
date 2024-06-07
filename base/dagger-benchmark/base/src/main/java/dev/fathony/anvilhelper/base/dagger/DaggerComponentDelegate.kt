package dev.fathony.anvilhelper.base.dagger

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.safeCast

internal class DaggerComponentDelegate<Type : Any, Factory : DaggerSubcomponentFactory, Target : Any>
constructor(
    private val factoryClass: KClass<Factory>,
    private val parentComponentProducer: Type.() -> Any,
    private val create: (Factory) -> DaggerComponent<Target>,
) : ReadOnlyProperty<Type, DaggerComponent<Target>> {

    private lateinit var component: DaggerComponent<Target>

    override fun getValue(thisRef: Type, property: KProperty<*>): DaggerComponent<Target> {
        if (!::component.isInitialized) {
            val parentComponent = parentComponentProducer(thisRef)

            val factory = factoryClass.safeCast(parentComponent)
                ?: error("The parent component ($parentComponent) does not implement $factoryClass.")

            component = create(factory)
        }
        return component
    }
}

fun <Type : Any, Factory : DaggerSubcomponentFactory, Target : Any> createComponentDelegate(
    factoryClass: KClass<Factory>,
    parentProducer: Type.() -> Any,
    create: (Factory) -> DaggerComponent<Target>,
): ReadOnlyProperty<Type, DaggerComponent<Target>> = DaggerComponentDelegate(
    factoryClass = factoryClass,
    parentComponentProducer = {
        val parent = parentProducer(this)
        val parentComponentOwner = parent as? DaggerComponentOwner
            ?: error("The specified parent ($parent) of $this does not implement DaggerComponentOwner.")
        parentComponentOwner.component
    },
    create = create,
)
