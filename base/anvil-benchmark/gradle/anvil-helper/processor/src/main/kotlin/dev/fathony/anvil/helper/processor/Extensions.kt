package dev.fathony.anvil.helper.processor

import com.squareup.anvil.annotations.ExperimentalAnvilApi
import com.squareup.anvil.compiler.internal.fqName
import com.squareup.anvil.compiler.internal.reference.AnnotationReference
import com.squareup.anvil.compiler.internal.reference.AnvilCompilationExceptionClassReference
import com.squareup.anvil.compiler.internal.reference.ClassReference
import com.squareup.anvil.compiler.internal.reference.Visibility
import com.squareup.anvil.compiler.internal.reference.argumentAt
import javax.inject.Scope

@OptIn(ExperimentalAnvilApi::class)
internal inline fun ClassReference.checkClassIsPublic(message: () -> String) {
    if (visibility() != Visibility.PUBLIC) {
        throw AnvilCompilationExceptionClassReference(
            classReference = this,
            message = message.invoke()
        )
    }
}

@OptIn(ExperimentalAnvilApi::class)
internal fun ClassReference.checkDefineEntryPointRequirement() {
    val daggerScopeCount = annotations.count { it.isDaggerScope() }
    if (daggerScopeCount == 0) {
        throw AnvilCompilationExceptionClassReference(
            message = "Classes annotated with @${DefineEntryPointFqName.shortName()} have to be annotated with a @Scope.",
            classReference = this
        )
    }
    if (daggerScopeCount > 1) {
        throw AnvilCompilationExceptionClassReference(
            message = "Classes annotated with @${DefineEntryPointFqName.shortName()} may not use more " +
                    "than one @Scope.",
            classReference = this
        )
    }

    val annotationCount = annotations.count { it.fqName == DefineEntryPointFqName }
    if (annotationCount > 1) {
        throw AnvilCompilationExceptionClassReference(
            message = "Classes may not use more than one @${DefineEntryPointFqName.shortName()} annotation",
            classReference = this
        )
    }

    val multipleEntryPointAnnotationPresent =
        annotations.any { it.fqName == DefineMultipleEntryPointFqName }
    if (multipleEntryPointAnnotationPresent) {
        throw AnvilCompilationExceptionClassReference(
            message = "Classes that annotated with @${DefineEntryPointFqName.shortName()} should not annotated with @${DefineMultipleEntryPointFqName.shortName()} annotation",
            classReference = this
        )
    }
}

@OptIn(ExperimentalAnvilApi::class)
internal fun ClassReference.checkDefineMultipleEntryPointRequirement() {
    val entryPointAnnotationPresent = annotations.any { it.fqName == DefineEntryPointFqName }
    if (entryPointAnnotationPresent) {
        throw AnvilCompilationExceptionClassReference(
            message = "Classes that annotated with @${DefineMultipleEntryPointFqName.shortName()} should not annotated with @${DefineEntryPointFqName.shortName()} annotation",
            classReference = this
        )
    }

    val multipleEntryAnnotationsCorrect =
        annotations.filter { it.fqName == DefineMultipleEntryPointFqName }
            .map { with(DefineMultipleEntryPointAnnotation) { it.daggerScopeOrNull() } }
            .all { it?.isAnnotatedWith(Scope::class.fqName) ?: false }
    
    if (!multipleEntryAnnotationsCorrect) {
        throw AnvilCompilationExceptionClassReference(
            message = "Property daggerScope in @${DefineMultipleEntryPointFqName.shortName()} annotations must be a Dagger Scope",
            classReference = this
        )
    }
}

@OptIn(ExperimentalAnvilApi::class)
internal fun AnnotationReference.parentScopeOrNull(): ClassReference? =
    argumentAt("parentScope", 1)?.value()

object DefineMultipleEntryPointAnnotation {
    @OptIn(ExperimentalAnvilApi::class)
    internal fun AnnotationReference.daggerScopeOrNull(): ClassReference? =
        argumentAt("daggerScope", 0)?.value()

    @OptIn(ExperimentalAnvilApi::class)
    internal fun AnnotationReference.keyOrNull(): ClassReference? =
        argumentAt("key", 1)?.value()

    @OptIn(ExperimentalAnvilApi::class)
    internal fun AnnotationReference.scopeOrNull(): ClassReference? =
        argumentAt("scope", 2)?.value()

    @OptIn(ExperimentalAnvilApi::class)
    internal fun AnnotationReference.parentScopeOrNull(): ClassReference? =
        argumentAt("parentScope", 3)?.value()
}
