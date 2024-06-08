package dev.fathony.anvil.helper.processor

import com.squareup.anvil.annotations.ExperimentalAnvilApi
import com.squareup.anvil.compiler.internal.buildFile
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import dev.fathony.anvil.helper.api.DefineEntryPoint
import dev.fathony.anvil.helper.api.DefineMultipleEntryPoint
import org.intellij.lang.annotations.Language
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

@ExperimentalAnvilApi
@Language("kotlin")
fun createSampleEntryPointKotlinClass(
    packageName: String,
    className: String,
    scope: KClass<*>
): String {
    return FileSpec.buildFile(
        packageName = packageName,
        fileName = className
    ) {
        addType(
            TypeSpec.classBuilder(className)
                .addAnnotation(Singleton::class)
                .addAnnotation(
                    AnnotationSpec.builder(DefineEntryPoint::class)
                        .addMember("scope = %T::class", scope)
                        .addMember("parentScope = %T::class", Unit::class)
                        .build()
                )
                .addProperty(
                    PropertySpec.builder("url", String::class)
                        .addAnnotation(
                            AnnotationSpec.builder(Inject::class)
                                .build()
                        )
                        .mutable()
                        .addModifiers(KModifier.LATEINIT)
                        .build()
                )
                .build()
        )
    }
}

@ExperimentalAnvilApi
@Language("kotlin")
fun createSampleMultipleEntryPointKotlinClass(
    packageName: String, 
    className: String, 
    daggerScope: KClass<*>, 
    key: KClass<*>,
    anvilScope: KClass<*>,
    parentAnvilScope: KClass<*>,
    daggerScope2: KClass<*>,
    key2: KClass<*>,
    anvilScope2: KClass<*>,
    parentAnvilScope2: KClass<*>
): String {
    return FileSpec.buildFile(
        packageName = packageName,
        fileName = className
    ) {
        addType(
            TypeSpec.classBuilder(className)
                .addAnnotation(
                    AnnotationSpec.builder(DefineMultipleEntryPoint::class)
                        .addMember("daggerScope = %T::class", daggerScope)
                        .addMember("key = %T::class", key)
                        .addMember("scope = %T::class", anvilScope)
                        .addMember("parentScope = %T::class", parentAnvilScope)
                        .build()
                )
                .addAnnotation(
                    AnnotationSpec.builder(DefineMultipleEntryPoint::class)
                        .addMember("daggerScope = %T::class", daggerScope2)
                        .addMember("key = %T::class", key2)
                        .addMember("scope = %T::class", anvilScope2)
                        .addMember("parentScope = %T::class", parentAnvilScope2)
                        .build()
                )
                .addProperty(
                    PropertySpec.builder("url", String::class)
                        .addAnnotation(
                            AnnotationSpec.builder(Inject::class)
                                .build()
                        )
                        .mutable()
                        .addModifiers(KModifier.LATEINIT)
                        .build()
                )
                .build()
        )
    }
}
