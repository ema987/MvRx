package com.airbnb.mvrx.stateresetter.annotations

/**
 * Can be used on classes which inherits from BaseMvRxViewModel.
 * It will help the annotation processor detects them and generate the correct functions to reset the viewModel's state.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class ResettableViewModel