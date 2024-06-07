import os
import shutil
from generators.dagger_component_generator.generate import generate
from generators.dagger_component_generator.modify import modify, appends_after_match


def get_normalized_working_dir() -> str:
    return os.path.dirname(os.path.abspath(__file__))


base_dir = os.path.join(get_normalized_working_dir(), "base")
dagger_benchmark_src_dir = os.path.join(base_dir, "dagger-benchmark")

generators_dir = os.path.join(get_normalized_working_dir(), "generators")
dagger_component_generator_dir = os.path.join(
    generators_dir, "dagger_component_generator"
)

result_dir = os.path.join(get_normalized_working_dir(), "result")
dagger_benchmark_result_dir = os.path.join(result_dir, "dagger-benchmark")


def add_gradle_submodules(module_names: list[str]):
    app_build_gradle = os.path.join(
        dagger_benchmark_result_dir, "app", "build.gradle.kts"
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


def register_dagger_submodules(modules: dict[str, str]):
    subproject_module = os.path.join(
        dagger_benchmark_result_dir,
        "app",
        "src",
        "main",
        "java",
        "dev",
        "fathony",
        "anvilhelper",
        "dagger",
        "di",
        "SubprojectModule.kt",
    )

    imports = list(
        map(
            lambda kv: "import dev.fathony.anvilhelper.{module_name}.{module}Module".format(
                module_name=kv[1], module=kv[0]
            ),
            modules.items(),
        )
    )
    classes = list(
        map(
            lambda kv: "        {module}Module::class,".format(module=kv[0]),
            modules.items(),
        )
    )

    appends_after_match(
        subproject_module, "// region Import module for subprojects", imports
    )
    appends_after_match(
        subproject_module, "// region Register module for subprojects", classes
    )


def register_activities_definition(
    module: str, module_name: str, activity_names: list[str]
):
    application_component = os.path.join(
        dagger_benchmark_result_dir,
        "app",
        "src",
        "main",
        "java",
        "dev",
        "fathony",
        "anvilhelper",
        "dagger",
        "di",
        "ApplicationComponent.kt",
    )
    import_activities = list(
        map(
            lambda i: "import dev.fathony.anvilhelper.{module_name}.{activity_name}Activity".format(
                module_name=module_name, activity_name=i
            ),
            activity_names,
        )
    )
    import_component = list(
        map(
            lambda i: "import dev.fathony.anvilhelper.{module_name}.{activity_name}ActivityComponent".format(
                module_name=module_name, activity_name=i
            ),
            activity_names,
        )
    )
    import_component_factory = list(
        map(
            lambda i: "import dev.fathony.anvilhelper.{module_name}.{activity_name}ActivityComponentFactory".format(
                module_name=module_name, activity_name=i
            ),
            activity_names,
        )
    )
    imports = import_activities + import_component + import_component_factory
    appends_after_match(
        application_component,
        "//region Importing activities injection definition",
        imports,
    )

    component_factories = list(
        map(
            lambda i: "    {activity_name}ActivityComponentFactory,".format(
                activity_name=i
            ),
            activity_names,
        )
    )
    appends_after_match(
        application_component,
        "//region Implement interfaces",
        component_factories,
    )

    abstract_factories = list(
        map(
            lambda i: "    abstract fun provide{module}{activity_name}ActivityComponentFactory(): MainActivityComponent.Factory".format(
                module=module, activity_name=i
            ),
            activity_names,
        )
    )

    overriden_functions = list(
        map(
            lambda i: "    override fun create{module}{activity_name}ActivityComponent(activity: {activity_name}Activity): {activity_name}ActivityComponent =\n        provide{module}{activity_name}ActivityComponentFactory().create(activity)".format(
                module=module, activity_name=i
            ),
            activity_names,
        )
    )
    function_body = abstract_factories + overriden_functions
    appends_after_match(
        application_component,
        "//region Activities registration",
        function_body,
    )


def generate_dagger_component():
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


# shutil.copytree(dagger_benchmark_src_dir, dagger_benchmark_result_dir)
# add_gradle_submodules(["foobar", "bazfoo", "barfoo"])
# register_dagger_submodules(
#     {
#         "FooBar": "foobar",
#         "FooBaz": "foobaz",
#     }
# )
register_activities_definition(
    module="BazFoo",
    module_name="bazfoo",
    activity_names=[
        "Page1",
        "Page2",
        "Page3",
    ],
)
