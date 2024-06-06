import cookiecutter.main as ccmain
import os
import shutil

root = "result"
activities_dir = os.path.join(root, "activities")
layouts_dir = os.path.join(root, "layout")


def generate_component(module: str, module_name: str):
    ccmain.cookiecutter(
        template="component",
        no_input=True,
        extra_context={
            "module": module,
            "module_name": module_name,
        },
        output_dir=root,
    )


def generate_activity(package_name: str, activity_name: str):
    ccmain.cookiecutter(
        template="activity",
        no_input=True,
        extra_context={
            "package_name": package_name,
            "activity_name": activity_name,
        },
        output_dir=activities_dir,
    )


def generate_layout(activity_name: str):
    ccmain.cookiecutter(
        template="layout",
        no_input=True,
        extra_context={
            "activity_name": activity_name,
        },
        output_dir=layouts_dir,
    )


def generate_module(module: str, module_name: str, activity_names: list[str]):
    assert len(activity_names) > 0, "Activity names should not be empty."
    generate_component(module, module_name)
    for name in activity_names:
        generate_activity(module_name, activity_name=name)
        generate_layout(activity_name=name)


def get_result_module_activity_folder(module_name: str) -> str:
    return os.path.join(
        root,
        module_name,
        "src",
        "main",
        "java",
        "dev",
        "fathony",
        "anvilhelper",
        module_name,
    )


def get_result_module_layout_folder(module_name: str) -> str:
    return os.path.join(root, module_name, "src", "main", "res", "layout")


def move_activities(module_name: str):
    subfolders = [f.path for f in os.scandir(activities_dir) if f.is_dir()]
    target = get_result_module_activity_folder(module_name)
    for folder in subfolders:
        names = [i.path for i in os.scandir(folder)]
        for name in names:
            shutil.move(name, target)
    shutil.rmtree(activities_dir)


def move_layouts(module_name: str):
    subfolders = [f.path for f in os.scandir(layouts_dir) if f.is_dir()]
    target = get_result_module_layout_folder(module_name)
    for folder in subfolders:
        names = [i.path for i in os.scandir(folder)]
        for name in names:
            shutil.move(name, target)
    shutil.rmtree(layouts_dir)


def generate(module: str, module_name: str, activity_names: list[str]):
    generate_module(module, module_name, activity_names)
    move_activities(module_name)
    move_layouts(module_name)


generate(
    module="FooBar",
    module_name="foobar",
    activity_names=[
        "Page1",
        "Page2",
        "Page3",
    ],
)
