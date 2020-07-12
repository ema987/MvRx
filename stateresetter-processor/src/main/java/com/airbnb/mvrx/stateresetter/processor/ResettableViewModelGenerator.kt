package com.airbnb.mvrx.stateresetter.processor

import com.airbnb.mvrx.stateresetter.annotations.ResettableProperty
import com.airbnb.mvrx.stateresetter.annotations.ResettableState
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.asTypeName
import com.sun.tools.javac.code.Symbol
import com.sun.tools.javac.code.Type
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element

/**
 * Generates extension functions for the given [viewModelElement]
 *
 * For each [ResettableProperty] found in [ResettableState] a new extension function will be generated.
 * The extension function will be like "resetNameOfTheProperty".
 * eg. If there is a property named "title", the generated extension function will be "resetTitle"
 *
 * Furthermore, will also generate a "reset" extension function which will reset all properties annotated with [ResettableProperty]
 */
fun generateResettableViewModelExtensions(processingEnv: ProcessingEnvironment, viewModelElement: Element): FileSpec {
    val resettableStateType = (viewModelElement as Symbol.ClassSymbol).superclass.typeArguments.singleOrNull { it.tsym.annotationMirrors.any { it.type.tsym.name.toString() == ResettableState::class.java.simpleName } }
            ?: throw RuntimeException("ViewModel annotated with ResettableViewModel must have State annotated with ResettableState")
    val resettableFields = resettableStateType.asElement().members().getElements { it.annotationMirrors.any { it.type.tsym.name.toString() == ResettableProperty::class.java.simpleName } }
    return FileSpec.builder(generatePackageName(processingEnv.elementUtils, viewModelElement), viewModelElement.simpleName.toString() + "Extensions").apply {
        generateResetPropertiesExtensions(this, viewModelElement, resettableStateType, resettableFields)
    }.build()
}

private fun generateResetPropertiesExtensions(builder: FileSpec.Builder, viewModelElement: Element, resettableStateType: Type, resettableFields: MutableIterable<Symbol>) {
    val resettableStateClassName = resettableStateType.tsym.name.toString()

    builder.addImport("com.airbnb.mvrx", "BaseMvRxViewModel")
    builder.addImport("com.airbnb.mvrx.stateresetter", "findBaseMvRxViewModelClass")

    resettableFields.forEach { resettableField ->
        builder.addFunction(FunSpec.builder("reset${resettableField.name.toString().capitalize()}")
                .receiver(viewModelElement.asType().asTypeName())
                .addStatement("val setStateFunction = findBaseMvRxViewModelClass(this::class.java).declaredMethods.singleOrNull { it.name == \"setState\" } ?: throw IllegalStateException(\"setState function not found!\")")
                .addStatement("setStateFunction.isAccessible = true")
                .addStatement("val newState: ($resettableStateClassName.() -> $resettableStateClassName) = {")
                .addStatement("copy(${resettableField.name} = $resettableStateClassName.${resettableField.getAnnotation(ResettableProperty::class.java).defaultValueConstantName})")
                .addStatement("}")
                .addStatement("setStateFunction.invoke(this, newState)")
                .build())
    }

    builder.addFunction(FunSpec.builder("reset")
            .receiver(viewModelElement.asType().asTypeName())
            .addStatement("val setStateFunction = findBaseMvRxViewModelClass(this::class.java).declaredMethods.singleOrNull { it.name == \"setState\" } ?: throw IllegalStateException(\"setState function not found!\")")
            .addStatement("setStateFunction.isAccessible = true")
            .addStatement("val newState: ($resettableStateClassName.() -> $resettableStateClassName) = {")
            .addStatement(generateCopyStatementForAllFields(resettableStateClassName, resettableFields))
            .addStatement("}")
            .addStatement("setStateFunction.invoke(this, newState)")
            .build())

}

private fun generateCopyStatementForAllFields(resettableStateClassName: String, resettableFields: MutableIterable<Symbol>): String {
    val resettableFieldsCopyString = resettableFields.joinToString(separator = ",") { resettableField ->
        "${resettableField.name} = $resettableStateClassName.${resettableField.getAnnotation(ResettableProperty::class.java).defaultValueConstantName}"
    }
    return "copy($resettableFieldsCopyString)"
}