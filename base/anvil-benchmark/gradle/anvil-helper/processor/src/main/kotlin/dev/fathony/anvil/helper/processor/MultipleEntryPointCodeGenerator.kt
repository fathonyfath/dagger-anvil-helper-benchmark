package dev.fathony.anvil.helper.processor

import com.google.auto.service.AutoService
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.ExperimentalAnvilApi
import com.squareup.anvil.annotations.MergeComponent
import com.squareup.anvil.annotations.MergeSubcomponent
import com.squareup.anvil.compiler.api.AnvilContext
import com.squareup.anvil.compiler.api.CodeGenerator
import com.squareup.anvil.compiler.api.GeneratedFile
import com.squareup.anvil.compiler.api.createGeneratedFile
import com.squareup.anvil.compiler.internal.buildFile
import com.squareup.anvil.compiler.internal.fqName
import com.squareup.anvil.compiler.internal.reference.ClassReference
import com.squareup.anvil.compiler.internal.reference.asClassName
import com.squareup.anvil.compiler.internal.reference.classAndInnerClassReferences
import com.squareup.anvil.compiler.internal.reference.generateClassName
import com.squareup.anvil.compiler.internal.safePackageString
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.STAR
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.metadata.KotlinPoetMetadataPreview
import com.squareup.kotlinpoet.metadata.toKmClass
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import dagger.multibindings.Multibinds
import dev.fathony.anvil.helper.api.EntryPoint
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.psi.KtFile
import java.io.File

@OptIn(ExperimentalAnvilApi::class)
@AutoService(CodeGenerator::class)
class MultipleEntryPointCodeGenerator : CodeGenerator {
    @OptIn(KotlinPoetMetadataPreview::class)
    override fun generateCode(
        codeGenDir: File,
        module: ModuleDescriptor,
        projectFiles: Collection<KtFile>
    ): Collection<GeneratedFile> {
        val factoryFunctions = EntryPoint.Factory::class.toKmClass().functions
        require(factoryFunctions.size == 1)
        val createFunctionRef = factoryFunctions.first { it.name == "create" }
        require(createFunctionRef.valueParameters.size == 1)
        val instanceParameterRef = createFunctionRef.valueParameters.first()

        return projectFiles
            .classAndInnerClassReferences(module)
            .filter { it.isAnnotatedWith(DefineMultipleEntryPointFqName) }
            .onEach { clazz ->
                clazz.checkClassIsPublic {
                    "${clazz.fqName} needs to be public to become an Entry Point."
                }
                clazz.checkDefineMultipleEntryPointRequirement()
            }
            .map { clazz ->
                val generatedPackageName = clazz.packageFqName.safePackageString(dotPrefix = true)

                clazz
                    .annotations
                    .filter { it.fqName == DefineMultipleEntryPointFqName }
                    .map { annotation ->
                        with(DefineMultipleEntryPointAnnotation) {
                            val daggerScope =
                                annotation.daggerScopeOrNull() ?: throw IllegalStateException()
                            val key = annotation.keyOrNull() ?: throw IllegalStateException()
                            val scope = annotation.scope()
                            val parentScope: ClassReference? = annotation.parentScopeOrNull()
                                ?.let { if (it.fqName == Unit::class.fqName) null else it }
                            
                            val entryPointClassName = key
                                .generateClassName(suffix = GeneratedEntryPointSuffix)
                                .relativeClassName
                                .toString()

                            val factoryAnnotation = if (parentScope == null) {
                                Component.Factory::class
                            } else {
                                Subcomponent.Factory::class
                            }

                            val entryPointFactoryInterfaceBuilder =
                                TypeSpec.interfaceBuilder(GeneratedEntryPointFactoryName)
                                    .addSuperinterface(
                                        EntryPoint.Factory::class.asClassName()
                                            .parameterizedBy(clazz.asClassName())
                                    )
                                    .addAnnotation(factoryAnnotation)
                                    .addFunction(
                                        FunSpec.builder(createFunctionRef.name)
                                            .addModifiers(KModifier.ABSTRACT, KModifier.OVERRIDE)
                                            .addParameter(
                                                ParameterSpec.builder(
                                                    instanceParameterRef.name,
                                                    clazz.asClassName()
                                                )
                                                    .addAnnotation(BindsInstance::class)
                                                    .build()
                                            )
                                            .returns(
                                                EntryPoint::class.asClassName().parameterizedBy(
                                                    clazz.asClassName()
                                                )
                                            )
                                            .build()
                                    )

                            val bindingModuleInterfaceBuilder = parentScope?.let { parent ->
                                TypeSpec.interfaceBuilder(GeneratedBindingModuleName)
                                    .addAnnotation(
                                        AnnotationSpec.builder(Module::class)
                                            .addMember(
                                                "subcomponents = [%T::class]",
                                                ClassName.bestGuess(entryPointClassName)
                                            )
                                            .build()
                                    )
                                    .addAnnotation(
                                        AnnotationSpec.builder(ContributesTo::class)
                                            .addMember("scope = %T::class", parent.asClassName())
                                            .build()
                                    )
                                    .addFunction(
                                        FunSpec.builder("emptyBinding")
                                            .addAnnotation(Multibinds::class)
                                            .addModifiers(KModifier.ABSTRACT)
                                            .returns(
                                                Map::class.asClassName().parameterizedBy(
                                                    Class::class.asClassName().parameterizedBy(STAR),
                                                    EntryPoint.Factory::class.asClassName()
                                                        .parameterizedBy(STAR)
                                                )
                                            )
                                            .build()
                                    )
                                    .addFunction(
                                        FunSpec.builder("bind${entryPointClassName}")
                                            .addAnnotation(Binds::class)
                                            .addAnnotation(IntoMap::class)
                                            .addAnnotation(
                                                AnnotationSpec.builder(ClassKey::class)
                                                    .addMember("value = %T::class", key.asClassName())
                                                    .build()
                                            )
                                            .addModifiers(KModifier.ABSTRACT)
                                            .addParameter(
                                                ParameterSpec.builder(
                                                    "factory",
                                                    ClassName.bestGuess(GeneratedEntryPointFactoryName)
                                                ).build()
                                            )
                                            .returns(
                                                EntryPoint.Factory::class.asClassName().parameterizedBy(
                                                    STAR
                                                )
                                            )
                                            .build()
                                    )
                            }

                            val componentAnnotation = if (parentScope == null) {
                                AnnotationSpec.builder(MergeComponent::class)
                                    .addMember("scope = %T::class", scope.asClassName())
                                    .build()
                            } else {
                                AnnotationSpec.builder(MergeSubcomponent::class)
                                    .addMember("scope = %T::class", scope.asClassName())
                                    .build()
                            }

                            val entryPointInterfaceBuilder = TypeSpec.interfaceBuilder(entryPointClassName)
                                .addSuperinterface(
                                    EntryPoint::class.asClassName().parameterizedBy(clazz.asClassName())
                                )
                                .addAnnotation(daggerScope.asClassName())
                                .addAnnotation(componentAnnotation)
                                .addType(entryPointFactoryInterfaceBuilder.build())
                                .run {
                                    bindingModuleInterfaceBuilder?.let { addType(it.build()) } ?: this
                                }

                            val content = FileSpec.buildFile(generatedPackageName, entryPointClassName) {
                                addType(entryPointInterfaceBuilder.build())
                            }
                            
                            createGeneratedFile(
                                codeGenDir = codeGenDir,
                                packageName = generatedPackageName,
                                fileName = entryPointClassName,
                                content = content
                            )
                        }
                    }
            }
            .flatMap { it }
            .toList()
    }

    override fun isApplicable(context: AnvilContext): Boolean = !context.generateFactories
}
