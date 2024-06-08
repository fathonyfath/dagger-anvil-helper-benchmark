package dev.fathony.anvil.helper.api

import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class DefineEntryPoint(
    val scope: KClass<*>,
    val parentScope: KClass<*> = Unit::class,
)
