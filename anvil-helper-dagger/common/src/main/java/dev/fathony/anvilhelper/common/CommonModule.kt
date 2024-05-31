package dev.fathony.anvilhelper.common

import dagger.Binds
import dagger.Module

@Module
interface CommonModule {
    @Binds
    fun bindNumberProvider(instance: RandomNumberProvider): NumberProvider
}
