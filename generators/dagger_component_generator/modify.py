import os

root = "result"


def get_module_file_path(module: str, module_name: str) -> str:
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
        "{module}Module.kt".format(module=module),
    )


def get_manifest_file_path(module_name: str) -> str:
    return os.path.join(root, module_name, "src", "main", "AndroidManifest.xml")


def appends_after_match(file: str, match: str, append: list[str]):
    with open(file, mode="r") as in_file:
        buffer = in_file.readlines()
        found_index = [buffer.index(l) for l in buffer if l.strip().startswith(match)]
        index = found_index[0]
        for i in append:
            index += 1
            buffer.insert(index, "{line}\n".format(line=i))
        with open(file, mode="w") as out_file:
            out_file.write("".join(buffer))


def register_activity_components(
    module: str, module_name: str, activity_names: list[str]
):
    module_path = get_module_file_path(module, module_name)
    result = list(
        map(
            lambda i: "        {module}{activity_name}Component::class,".format(
                module=module, activity_name=i
            ),
            activity_names,
        )
    )
    appends_after_match(
        module_path,
        match="// region Register activity components",
        append=result,
    )


def provide_page_for_activities(
    module: str, module_name: str, activity_names: list[str]
):
    module_path = get_module_file_path(module, module_name)

    def create_provider(module: str, activity_name: str) -> str:
        return '        @Provides\n        @IntoSet\n        @Named("{module}")\n        fun provide{module}{activity_name}Page(builder: {module}{activity_name}.Builder): Page {{\n            return Page("{activity_name}", builder)\n        }}'.format(
            module=module,
            activity_name=activity_name,
        )

    result = list(map(lambda i: create_provider(module, i), activity_names))
    appends_after_match(
        module_path,
        match="// region Provide page for each activity",
        append=result,
    )


def update_manifest(module: str, module_name: str, activity_names: list[str]):
    manifest_path = get_manifest_file_path(module_name)
    result = list(
        map(
            lambda i: '        <activity android:name=".{module}{activity_name}" />'.format(
                module=module, activity_name=i
            ),
            activity_names,
        )
    )
    appends_after_match(
        manifest_path,
        match="<!-- region Register activities -->",
        append=result,
    )


def modify(module: str, module_name: str, activity_names: list[str]):
    activity_names = list(
        map(lambda i: "{name}Activity".format(name=i), activity_names)
    )
    register_activity_components(module, module_name, activity_names)
    provide_page_for_activities(module, module_name, activity_names)
    update_manifest(module, module_name, activity_names)
