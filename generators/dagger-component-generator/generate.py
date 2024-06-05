import cookiecutter.main as ccmain

ccmain.cookiecutter(
    "component",
    no_input=True,
    extra_context={
        "module": "Random",
        "module_name": "random",
        "package_name": "random",
    },
    output_dir="../../anvil-helper-dagger",
)
