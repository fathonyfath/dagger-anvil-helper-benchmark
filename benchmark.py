import os

dagger_folders = [
    "dagger-1-10", 
    "dagger-2-10",
    "dagger-3-10",
    "dagger-4-10",
    "dagger-5-10",
    "dagger-6-10",
    "dagger-7-10",
    "dagger-8-10",
    "dagger-9-10",
    "dagger-10-10",
]

anvil_folders = [
    "anvil-1-10", 
    "anvil-2-10",
    "anvil-3-10",
    "anvil-4-10",
    "anvil-5-10",
    "anvil-6-10",
    "anvil-7-10",
    "anvil-8-10",
    "anvil-9-10",
    "anvil-10-10",
]

def get_normalized_working_dir() -> str:
    return os.path.dirname(os.path.abspath(__file__))

result_dir = os.path.join(get_normalized_working_dir(), "result")
benchmark_result_dir = os.path.join(get_normalized_working_dir(), "benchmark")
scenario_file = os.path.join(get_normalized_working_dir(), "benchmark.scenario")

def benchmark(project_dir: str, output_dir: str):
    os.system('gradle-profiler --project-dir "{project_dir}" --scenario-file "{scenario_file}" --warmups 2 --benchmark --output-dir "{output_dir}"'.format(
        project_dir=os.path.join(result_dir, project_dir),
        scenario_file=scenario_file,
        output_dir=os.path.join(benchmark_result_dir, output_dir),
    ))

for dagger in dagger_folders:
    benchmark(dagger, dagger)

for anvil in anvil_folders:
    benchmark(anvil, anvil)
