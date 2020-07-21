package com.airbnb.mvrx.stateresetter.sample.annotation

/**
 * Created by emanuele on 21/07/2020
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class ResettableProperty(val defaultValueConstantName: String)