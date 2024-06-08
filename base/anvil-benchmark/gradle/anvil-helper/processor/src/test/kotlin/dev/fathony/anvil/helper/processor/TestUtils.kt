package dev.fathony.anvil.helper.processor

import com.squareup.anvil.annotations.ExperimentalAnvilApi
import com.squareup.anvil.compiler.api.CodeGenerator
import com.squareup.anvil.compiler.internal.testing.AnvilCompilationMode
import com.squareup.anvil.compiler.internal.testing.compileAnvil
import com.tschuchort.compiletesting.CompilationResult
import com.tschuchort.compiletesting.JvmCompilationResult
import org.intellij.lang.annotations.Language
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

@OptIn(ExperimentalCompilerApi::class, ExperimentalAnvilApi::class)
internal fun compile(
    @Language("kotlin") vararg sources: String,
    previousCompilationResult: JvmCompilationResult? = null,
    enableDebuggerAnnotationProcessor: Boolean = false,
    codeGenerators: List<CodeGenerator> = emptyList(),
    allWarningAsErrors: Boolean = false,
    mode: AnvilCompilationMode = AnvilCompilationMode.Embedded(codeGenerators),
    block: JvmCompilationResult.() -> Unit = {}
): CompilationResult = compileAnvil(
    sources = sources,
    allWarningsAsErrors = allWarningAsErrors,
    previousCompilationResult = previousCompilationResult,
    enableDaggerAnnotationProcessor = enableDebuggerAnnotationProcessor,
    mode = mode,
    block = block
)
