package com.airbnb.mvrx.stateresetter.annotations

/**
 * Can be used on classes which implements the MvRxState interface.
 * It will help the annotation processor detects them and generate the correct functions to reset the state.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class ResettableState