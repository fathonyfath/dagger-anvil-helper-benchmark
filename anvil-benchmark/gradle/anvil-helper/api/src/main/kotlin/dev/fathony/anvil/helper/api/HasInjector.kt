package dev.fathony.anvil.helper.api

interface HasInjector {
    fun injector(): DispatchingInjector<Any>
}
