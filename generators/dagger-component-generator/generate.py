import cookiecutter.main as ccmain
import os


def generate_component(module: str, module_name: str):
    ccmain.cookiecutter(
        template="component",
        no_input=True,
        extra_context={
            "module": module,
            "module_name": module_name,
        },
        output_dir="./result",
    )


def generate_activity(package_name: str, activity_name: str):
    ccmain.cookiecutter(
        template="activity",
        no_input=True,
        extra_context={
            "package_name": package_name,
            "activity_name": activity_name,
        },
        output_dir="./result/activities",
    )


def generate_layout(activity_name: str):
    ccmain.cookiecutter(
        template="layout",
        no_input=True,
        extra_context={
            "activity_name": activity_name,
        },
        output_dir="./result/layouts",
    )


def generate_module(module: str, module_name: str, activity_names: list[str]):
    assert len(activity_names) > 0, "Activity names should not be empty."
    generate_component(module=module, module_name=module_name)
    for name in activity_names:
        generate_activity(package_name=module_name, activity_name=name)
        generate_layout(activity_name=name)


generate_module(
    module="FooBar",
    module_name="foobar",
    activity_names=[
        "Page1",
        "Page2",
        "Page3",
    ],
)
