package dev.fathony.anvilhelper.component1

import dagger.BindsInstance
import dagger.Subcomponent
import dev.fathony.anvilhelper.base.dagger.DaggerComponent
import dev.fathony.anvilhelper.base.dagger.DaggerSubcomponentFactory
import dev.fathony.anvilhelper.base.dagger.scope.ActivityScope

@ActivityScope
@Subcomponent
interface Page1ActivityComponent : DaggerComponent<Page1Activity> {
    @Subcomponent.Factory
    interface Factory : DaggerSubcomponentFactory {
        fun create(@BindsInstance target: Page1Activity): Page1ActivityComponent
    }
}
