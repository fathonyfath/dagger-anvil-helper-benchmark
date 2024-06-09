import os
import shutil
from generators.anvil_component_generator.generate import generate
from generators.anvil_component_generator.modify import modify, appends_after_match
import random
import string
import argparse


parser = argparse.ArgumentParser(description="Generate the Anvil Benchmark")
parser.add_argument(
    "-m",
    "--module",
    help="Number of modules that will be generated",
    required=False,
    default=1,
)
parser.add_argument(
    "-a",
    "--activity",
    help="Number of activities that will be generated for each module",
    required=False,
    default=1,
)
parser.add_argument(
    "-o",
    "--output",
    help="The name of the folder that will be the output of this command",
    required=False,
    default="anvil-benchmark",
)
args = parser.parse_args()
module_count = int(args.module)
activity_count = int(args.activity)
output_folder_name = args.output


def get_normalized_working_dir() -> str:
    return os.path.dirname(os.path.abspath(__file__))


base_dir = os.path.join(get_normalized_working_dir(), "base")
anvil_benchmark_src_dir = os.path.join(base_dir, "anvil-benchmark")

generators_dir = os.path.join(get_normalized_working_dir(), "generators")
anvil_component_generator_dir = os.path.join(
    generators_dir, "anvil_component_generator"
)

result_dir = os.path.join(get_normalized_working_dir(), "result")
anvil_benchmark_result_dir = os.path.join(result_dir, output_folder_name)


def add_gradle_submodules(module_names: list[str]):
    app_build_gradle = os.path.join(
        anvil_benchmark_result_dir, "app", "build.gradle.kts"
    )
    modules = list(
        map(
            lambda i: '    implementation(project(":{module_name}"))'.format(
                module_name=i
            ),
            module_names,
        )
    )
    appends_after_match(app_build_gradle, "// region Subproject modules", modules)


def generate_anvil_component(module: str, module_name: str, activity_names: list[str]):
    os.chdir(anvil_component_generator_dir)
    generate(module, module_name, activity_names)
    modify(module, module_name, activity_names)


def generate_anvil_benchmark(structure: dict[str, list[str]]):
    shutil.copytree(anvil_benchmark_src_dir, anvil_benchmark_result_dir)
    for key, value in structure.items():
        module = key.replace(" ", "")
        module_name = module.lower()
        activity_names = list(map(lambda i: i.replace(" ", ""), value))

        generate_anvil_component(module, module_name, activity_names)
        shutil.move(
            os.path.join(anvil_component_generator_dir, "result", module_name),
            os.path.join(anvil_benchmark_result_dir, module_name),
        )

    module_names = list(
        map(lambda kv: kv[0].replace(" ", "").lower(), structure.items())
    )
    add_gradle_submodules(module_names)


def generate_random_names(count: int) -> set[str]:
    starting_length = 4
    result = set()
    while len(result) < count:
        random_string = "".join(
            random.choices(string.ascii_lowercase, k=starting_length)
        ).capitalize()
        if random_string in result:
            starting_length += 1
        else:
            result.add(random_string)

    return result


modules = generate_random_names(module_count)
structure = dict(
    map(lambda i: (i, list(generate_random_names(activity_count))), modules)
)
generate_anvil_benchmark(structure)
