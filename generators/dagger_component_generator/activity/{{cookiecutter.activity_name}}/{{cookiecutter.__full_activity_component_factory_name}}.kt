package {{ cookiecutter.__full_package_name }}

import dev.fathony.anvilhelper.base.dagger.DaggerSubcomponentFactory

interface {{ cookiecutter.__full_activity_component_factory_name }} : DaggerSubcomponentFactory {
    fun {{ cookiecutter.__activity_component_factory_create_function_name }}(activity: {{ cookiecutter.__full_activity_name }}): {{ cookiecutter.__full_activity_component_name }}
}
