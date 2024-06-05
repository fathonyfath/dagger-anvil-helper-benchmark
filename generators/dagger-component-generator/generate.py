import cookiecutter.main as ccmain


def generate_component(module: str, module_name: str, package_name: str):
    ccmain.cookiecutter(
        template="component",
        no_input=True,
        extra_context={
            "module": module,
            "module_name": module_name,
            "package_name": package_name,
        },
    )


def generate_activity(package_name: str, activity_name: str):
    ccmain.cookiecutter(
        template="activity",
        no_input=True,
        extra_context={
            "package_name": package_name,
            "activity_name": activity_name,
        },
    )


def generate_layout(activity_name: str):
    ccmain.cookiecutter(
        template="layout",
        no_input=True,
        extra_context={
            "activity_name": activity_name,
        },
    )


generate_component(
    module="FooBar",
    module_name="foobar",
    package_name="foobar",
)
