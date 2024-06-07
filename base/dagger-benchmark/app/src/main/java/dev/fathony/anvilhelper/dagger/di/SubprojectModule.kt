package dev.fathony.anvilhelper.dagger.di

import dagger.Module
import dagger.multibindings.Multibinds
import dev.fathony.anvilhelper.base.page.PageGroup

// region Import module for subprojects

// endregion

@Module(
    includes = [
        // region Register module for subprojects
        
        // endregion
    ]
)
interface SubprojectModule {

    @Multibinds
    fun pageGroups(): Set<PageGroup>
}
