package dev.fathony.anvil.helper.api

interface EntryPoint<T> {

    fun inject(instance: T)

    interface Factory<T> {
        fun create(instance: T): EntryPoint<T>
    }
}
