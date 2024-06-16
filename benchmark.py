import os

dagger_folders = [
    "dagger-5-10", 
    "dagger-10-10",
    "dagger-15-15",
    "dagger-20-20",
    "dagger-25-25",
]

anvil_folders = [
    "anvil-5-10", 
    "anvil-10-10",
    "anvil-15-15",
    "anvil-20-20",
    "anvil-25-25",
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

for anvil in anvil_folders:
    benchmark(anvil, anvil)

for dagger in dagger_folders:
    benchmark(dagger, dagger)