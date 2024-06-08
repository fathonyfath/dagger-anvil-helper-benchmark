package dev.fathony.anvil.helper.api

import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Repeatable
annotation class DefineMultipleEntryPoint(
    val daggerScope: KClass<*>,
    val key: KClass<*>,
    val scope: KClass<*>,
    val parentScope: KClass<*> = Unit::class
)
