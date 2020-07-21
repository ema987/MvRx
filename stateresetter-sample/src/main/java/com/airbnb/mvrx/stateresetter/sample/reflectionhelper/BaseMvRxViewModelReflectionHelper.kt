package com.airbnb.mvrx.stateresetter.sample.reflectionhelper

import com.airbnb.mvrx.BaseMvRxViewModel

/**
 * Returns the [Class] of [BaseMvRxViewModel] found as superclass of [clazz]
 */
fun findBaseMvRxViewModelClass(clazz : Class<*>): Class<*> {
    var superClass = clazz.superclass
    while(superClass != null) {
        when(superClass.simpleName) {
                BaseMvRxViewModel::class.simpleName -> return superClass
            else -> superClass = superClass.superclass
        }
    }
    throw IllegalStateException("Class ${clazz.simpleName} must inherit from BaseMvRxViewModel")
}