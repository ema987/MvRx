package com.airbnb.mvrx.stateresetter.processor

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

/**
 * Generates an helper class named BaseMvRxViewModelReflectionHelper with a function findBaseMvRxViewModelClass which looks for BaseMvRxViewModel given a class
 */
fun generateReflectionHelper(): FileSpec {
    return FileSpec.builder(STATE_RESETTER_PACKAGE_NAME, "BaseMvRxViewModelReflectionHelper").apply {
        addFunction(FunSpec.builder("findBaseMvRxViewModelClass")
                .addParameter("clazz", Class::class.asClassName().parameterizedBy(STAR))
                .addStatement("var superClass = clazz.superclass")
                .addStatement("while(superClass != null) {")
                .addStatement("when(superClass.simpleName) {")
                .addStatement("\"BaseMvRxViewModel\" -> return superClass")
                .addStatement("else -> superClass = superClass.superclass")
                .addStatement("}")
                .addStatement("}")
                .addStatement("throw IllegalStateException(\"Class \${clazz.simpleName} must inherit from BaseMvRxViewModel\")")
                .returns(Class::class.asClassName().parameterizedBy(STAR))
                .build()
        )
    }.build()
}
