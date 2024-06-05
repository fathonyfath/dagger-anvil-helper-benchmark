package dev.fathony.anvilhelper.component1

import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import dev.fathony.anvilhelper.base.page.CrashingIntentBuilder
import dev.fathony.anvilhelper.base.page.IntentBuilder
import dev.fathony.anvilhelper.base.page.Page
import dev.fathony.anvilhelper.base.page.PageGroup
import javax.inject.Named

@Module(
    subcomponents = [
        // region Register activity components

        // endregion
    ]
)
interface Component1Module {

    @Multibinds
    @Named("Component1")
    fun pages(): Set<Page>

    companion object {
        @Provides
        @IntoSet
        fun providePageGroup(@Named("Component1") pages: Set<Page>): PageGroup {
            return PageGroup(
                name = "Component1",
                pages = pages.sortedBy { it.name },
            )
        }

        // region Provide page for each activity
        
        // endregion        
        @Provides
        @IntoSet
        @Named("Component1")
        fun providePages1(builder: Page1Activity.Builder): Page {
            return Page("Component1Page1", builder)
        }
    }
}
