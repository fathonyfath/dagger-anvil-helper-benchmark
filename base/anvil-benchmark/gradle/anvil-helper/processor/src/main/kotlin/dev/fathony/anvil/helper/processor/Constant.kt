package dev.fathony.anvil.helper.processor

import com.squareup.anvil.annotations.ExperimentalAnvilApi
import com.squareup.anvil.compiler.internal.fqName
import dev.fathony.anvil.helper.api.DefineEntryPoint
import dev.fathony.anvil.helper.api.DefineMultipleEntryPoint

@OptIn(ExperimentalAnvilApi::class)
val DefineEntryPointFqName = DefineEntryPoint::class.fqName
@OptIn(ExperimentalAnvilApi::class)
val DefineMultipleEntryPointFqName = DefineMultipleEntryPoint::class.fqName

const val GeneratedEntryPointSuffix = "EntryPoint"
const val GeneratedBindingModuleName = "BindingModule"
const val GeneratedEntryPointFactoryName = "Factory"
