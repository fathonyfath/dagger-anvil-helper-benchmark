import cookiecutter.main as ccmain


def generate_component():
    ccmain.cookiecutter(
        "component",
        no_input=True,
        extra_context={
            "module": "Random",
            "module_name": "random",
            "package_name": "random",
        },
    )


def generate_activity():
    ccmain.cookiecutter(
        "activity",
        no_input=True,
        extra_context={
            "package_name": "random",
            "activity_name": "Random",
        },
    )


generate_activity()
