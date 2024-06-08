package dev.fathony.anvil.helper.processor

import com.squareup.anvil.annotations.ExperimentalAnvilApi
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import javax.inject.Scope
import javax.inject.Singleton
import kotlin.test.Test

@Suppress("UnnecessaryOptInAnnotation")
@OptIn(ExperimentalCompilerApi::class, ExperimentalAnvilApi::class)
class EntryPointCodeGeneratorTest {

    @Test
    fun testEntryPointCodeGenerator() {
        val packageName = "dev.fathony.anvil.helper.sample"
        val className = "SampleComponent"

        val content = createSampleEntryPointKotlinClass(
            packageName = packageName,
            className = className,
            scope = Singleton::class
        )

        compile(
            content,
            codeGenerators = listOf(EntryPointCodeGenerator(), MultipleEntryPointCodeGenerator())
        ) {
            val generatedPackage = "${packageName}."
            val generatedClassName = className.plus(GeneratedEntryPointSuffix)
            @Suppress("UNUSED_VARIABLE") val generatedClass =
                classLoader.loadClass("${generatedPackage}${generatedClassName}")
        }
    }

    @Test
    fun testMultipleEntryPointCodeGenerator() {
        val packageName = "dev.fathony.anvil.helper.sample"
        val className = "SampleComponent"

        val content = createSampleMultipleEntryPointKotlinClass(
            packageName = packageName,
            className = className,
            daggerScope = Singleton::class,
            key = SampleComponentFirstKey::class,
            anvilScope = SampleComponentFirstScope::class,
            parentAnvilScope = ParentScope::class,
            daggerScope2 = ChildScope::class,
            key2 = SampleComponentSecondKey::class,
            anvilScope2 = SampleComponentSecondScope::class,
            parentAnvilScope2 = AnotherParentScope::class
        )

        compile(
            content,
            codeGenerators = listOf(EntryPointCodeGenerator(), MultipleEntryPointCodeGenerator())
        ) {
            val generatedPackage = "${packageName}."
            val generatedClassName = className.plus(GeneratedEntryPointSuffix)
            @Suppress("UNUSED_VARIABLE") val generatedClass =
                classLoader.loadClass("${generatedPackage}${generatedClassName}")

        }
    }
}

@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class ChildScope

abstract class ParentScope private constructor()
abstract class AnotherParentScope private constructor()
abstract class SampleComponentFirstScope private constructor()
abstract class SampleComponentSecondScope private constructor()
abstract class SampleComponentFirstKey private constructor()
abstract class SampleComponentSecondKey private constructor()
