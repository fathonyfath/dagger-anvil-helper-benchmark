package {{ cookiecutter.__full_package_name }}

import dagger.BindsInstance
import dagger.Subcomponent
import dev.fathony.anvilhelper.base.dagger.DaggerComponent
import dev.fathony.anvilhelper.base.dagger.DaggerSubcomponentFactory
import dev.fathony.anvilhelper.base.dagger.scope.ActivityScope

@ActivityScope
@Subcomponent
interface {{ cookiecutter.__full_activity_component_name }} : DaggerComponent<{{ cookiecutter.__full_activity_name }}> {
    @Subcomponent.Factory
    interface Factory : DaggerSubcomponentFactory {
        fun create(@BindsInstance target: {{ cookiecutter.__full_activity_name }}): {{ cookiecutter.__full_activity_component_name }}
    }
}
