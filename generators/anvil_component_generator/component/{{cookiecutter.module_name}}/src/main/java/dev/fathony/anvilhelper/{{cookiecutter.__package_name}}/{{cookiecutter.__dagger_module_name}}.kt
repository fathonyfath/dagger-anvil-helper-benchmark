package {{ cookiecutter.__full_package_name }}

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import dev.fathony.anvilhelper.base.anvil.ApplicationScope
import dev.fathony.anvilhelper.base.page.Page
import dev.fathony.anvilhelper.base.page.PageGroup
import javax.inject.Named

@Module
@ContributesTo(ApplicationScope::class)
interface FooBarModule {

    @Multibinds
    @Named("FooBar")
    fun pages(): Set<Page>

    companion object {
        @Provides
        @IntoSet
        fun providePageGroup(@Named("FooBar") pages: Set<Page>): PageGroup {
            return PageGroup(
                name = "FooBar",
                pages = pages.sortedBy { it.name },
            )
        }

        // region Provide page for each activity
        // endregion
    }
}
