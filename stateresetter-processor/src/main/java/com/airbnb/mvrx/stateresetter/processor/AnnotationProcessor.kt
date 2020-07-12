package com.airbnb.mvrx.stateresetter.processor

import com.airbnb.mvrx.stateresetter.annotations.ResettableProperty
import com.airbnb.mvrx.stateresetter.annotations.ResettableState
import com.airbnb.mvrx.stateresetter.annotations.ResettableViewModel
import com.google.auto.service.AutoService
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
class AnnotationProcessor: AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(ResettableViewModel::class.java.name, ResettableState::class.java.name, ResettableProperty::class.java.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latest()

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        //TODO add checks for annotations used on correct classes
        writeKotlinFile(processingEnv) {
            generateReflectionHelper()
        }
        val resettableViewModelElements = roundEnv.getElementsAnnotatedWith(ResettableViewModel::class.java)
        resettableViewModelElements.forEach { resettableViewModelElement ->
            writeKotlinFile(processingEnv) {
                generateResettableViewModelExtensions(processingEnv, resettableViewModelElement)
            }
        }
        return true
    }

}
