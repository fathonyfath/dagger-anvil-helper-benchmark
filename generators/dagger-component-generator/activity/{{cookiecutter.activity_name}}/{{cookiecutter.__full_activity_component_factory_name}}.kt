package {{cookiecutter.__full_package_name}}

import dev.fathony.anvilhelper.base.dagger.DaggerSubcomponentFactory

interface {{cookiecutter.__full_activity_component_factory_name}} : DaggerSubcomponentFactory {
    fun create{{cookiecutter.__full_activity_component_name}}(activity: {{cookiecutter.__full_activity_name}}): {{cookiecutter.__full_activity_component_name}}
}
