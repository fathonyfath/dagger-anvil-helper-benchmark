// Include all sub-folders that contains a 'build.gradle.kts' as subprojects
rootDir.listFiles()?.filter { File(it, "build.gradle.kts").exists() }?.forEach { subproject ->
    include(subproject.name)
}
