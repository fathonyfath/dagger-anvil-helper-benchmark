package dev.fathony.anvilhelper.common

import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module
import dev.fathony.anvilhelper.base.anvil.ApplicationScope

@Module
@ContributesTo(ApplicationScope::class)
interface CommonModule {
    @Binds
    fun bindNumberProvider(instance: RandomNumberProvider): NumberProvider
}
