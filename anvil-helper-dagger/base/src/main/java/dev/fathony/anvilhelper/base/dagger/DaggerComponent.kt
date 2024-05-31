package dev.fathony.anvilhelper.base.dagger

interface DaggerComponent<T : Any> {
    fun inject(target: T)
}
