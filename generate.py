import os
import shutil
from generators.dagger_component_generator.generate import generate
from generators.dagger_component_generator.modify import modify

generators_dir = "generators"
dagger_component_generator_dir = os.path.join(
    generators_dir, "dagger_component_generator"
)


def get_normalized_working_dir() -> str:
    return os.path.dirname(os.path.abspath(__file__))


def normalize_working_dir():
    os.chdir(get_normalized_working_dir())


def generate_dagger_component():
    normalize_working_dir()
    os.chdir(dagger_component_generator_dir)
    generate(
        module="BazFoo",
        module_name="bazfoo",
        activity_names=[
            "Page1",
            "Page2",
            "Page3",
        ],
    )
    modify(
        module="BazFoo",
        module_name="bazfoo",
        activity_names=[
            "Page1",
            "Page2",
            "Page3",
        ],
    )


generate_dagger_component()
