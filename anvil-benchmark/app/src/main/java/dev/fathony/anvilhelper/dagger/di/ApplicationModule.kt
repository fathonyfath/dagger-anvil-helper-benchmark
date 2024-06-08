package dev.fathony.anvilhelper.dagger.di

import dagger.Module
import dagger.multibindings.Multibinds
import dev.fathony.anvilhelper.base.page.PageGroup

@Module
interface ApplicationModule {

    @Multibinds
    fun pageGroups(): Set<PageGroup>
}
