package com.airbnb.mvrx.stateresetter.processor

import javax.lang.model.element.Element
import javax.lang.model.util.Elements

/**
 * Returns the package name for the given [element]
 */
fun generatePackageName(elements: Elements, element: Element): String {
    val pkg = elements.getPackageOf(element)
    if (pkg.isUnnamed) {
        throw RuntimeException(element.simpleName.toString())
    }
    return pkg.qualifiedName.toString()
}

const val STATE_RESETTER_PACKAGE_NAME = "com.airbnb.mvrx.stateresetter"