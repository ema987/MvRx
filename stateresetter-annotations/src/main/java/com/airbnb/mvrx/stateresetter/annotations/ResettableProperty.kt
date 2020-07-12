package com.airbnb.mvrx.stateresetter.annotations

/**
 *
 * Can be used on the properties of classes which implements the MvRxState interface.
 * It will help the annotation processor detects them and generate the correct functions to reset the annotated property.
 *
 * [defaultValueConstantName] is the name of the constant field that should be used as the default value for this prop. The
 * default value will be used if the prop value isn't set on the model.
 * <p>
 * For example, you would define a constant in your view class like <code>static final int
 * DEFAULT_NUM_LINES = 3</code>, and then set this parameter to "DEFAULT_NUM_LINES" so that the
 * annotation processor knows what constant to reference.
 * <p>
 * The name of the constant must be used instead of referencing the constant directly since
 * objects are not valid annotation parameters.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class ResettableProperty(val defaultValueConstantName: String)