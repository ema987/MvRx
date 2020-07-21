package com.airbnb.mvrx.stateresetter.sample

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.BuildConfig
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.stateresetter.sample.annotation.ResettableProperty
import com.airbnb.mvrx.stateresetter.sample.reflectionhelper.findBaseMvRxViewModelClass
import java.lang.reflect.Modifier
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberProperties

@Suppress("UNCHECKED_CAST")
abstract class MvRxViewModel<S : MvRxState>(initialState: S) : BaseMvRxViewModel<S>(initialState, debugMode = BuildConfig.DEBUG) {

    init {
        //warmUp kotlin reflection cache
        reset()
    }

    /**
     * Resets all properties of [S] which are annotated with [ResettableProperty] to their default value
     */
    fun reset() {
        withState { state ->
            val stateAnnotatedResettableMemberProperties = state::class.memberProperties.filter { it.findAnnotation<ResettableProperty>() != null }
            reset(*stateAnnotatedResettableMemberProperties.toTypedArray())
        }
    }

    /**
     * Resets all [propertiesToReset] of [S] which are annotated with [ResettableProperty] to their default value
     */
    fun <T> reset(vararg propertiesToReset: KProperty1<out S, T>) {
        val startMillis = System.currentTimeMillis()
        val setStateFunction = findBaseMvRxViewModelClass(this::class.java).declaredMethods.singleOrNull {
            it.name == "setState"
        } ?: throw IllegalStateException("setState function not found!")
        setStateFunction.isAccessible = true
        val newState: (S.() -> S) = {
            val stateProperties = this::class.memberProperties

            val staticStateFields = this::class.java.declaredFields.filter { Modifier.isStatic(it.modifiers) }
            val functions = this::class.functions

            val stateAnnotatedResettableMemberProperties = this::class.memberProperties.filter { it.findAnnotation<ResettableProperty>() != null }

            for (propertyToReset in propertiesToReset) {
                if (propertyToReset !in stateAnnotatedResettableMemberProperties) {
                    throw IllegalArgumentException("${propertyToReset.name} must be annotated with @ResettableProperty to be reset!")
                }
            }

            val annotatedToBeResetProperties = stateAnnotatedResettableMemberProperties.filter { field ->
                field.name in propertiesToReset.map { it.name }
            }.mapNotNull { field ->
                val annotation = field.annotations.singleOrNull { it is ResettableProperty } as ResettableProperty?
                if (annotation != null) {
                    field.name to annotation.defaultValueConstantName
                } else {
                    null
                }
            }.toMap()

            val newValuesMap = stateProperties.map { property ->
                if (property.name in annotatedToBeResetProperties.keys) {
                    val companionFieldName = annotatedToBeResetProperties[property.name]
                    property.name to staticStateFields.singleOrNull { it.name == companionFieldName }?.get(this)
                } else {
                    val nonResettableProperty = stateProperties.single { it.name == property.name }
                    property.name to nonResettableProperty.call(this)
                }
            }.toMap()

            val copyMethod = functions.single { it.name == "copy" }
            val copyMethodParametersOrder = copyMethod.parameters.map { it.name }

            val orderByParameterNameMap = copyMethodParametersOrder.withIndex().associate { it.value to it.index }
            val sortedNewValues = newValuesMap.toSortedMap(compareBy { orderByParameterNameMap[it] })

            val newState = copyMethod.call(this, *sortedNewValues.values.toTypedArray()) as S
            val endMillis = System.currentTimeMillis()
            println("Duration ${endMillis - startMillis}ms")
            newState
        }
        setStateFunction.invoke(this, newState)
    }

}