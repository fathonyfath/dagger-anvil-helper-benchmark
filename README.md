# Dagger Anvil Helper Benchmark

## Guide

To generate sample project, set the Python environment by installing `requirements.txt`.

Then run one of the following python script;`generate_dagger_benchmark.py` or `generate_anvil_benchmark.py`.

There are parameters available to put when running the scripts: 

* `-m` or `--module` to determine the number of modules that will be generated. 
  
  Default is 1.

* `-a` or `--activity` to determine the number of activities per module that will be generated.

  Default is 1.

* `-o` or `--output` to determine what should be the resulting folder name.

  Default for `generate_anvil_benchmark.py` is `anvil_benchmark`. For `generate_dagger_benchmark` is `dagger` benchmark.
