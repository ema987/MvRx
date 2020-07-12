package com.airbnb.mvrx.stateresetter.processor

import com.squareup.kotlinpoet.FileSpec
import java.io.File
import java.io.IOException
import javax.annotation.processing.ProcessingEnvironment

/**
 * Writes a Kotlin file specified by [fileSpecGenerator] in the Kotlin generated files folder
 */
@Throws(IOException::class)
fun writeKotlinFile(processingEnvironment: ProcessingEnvironment, fileSpecGenerator: () -> FileSpec) {
    val fileSpec = fileSpecGenerator()
    val options = processingEnvironment.options
    val generatedPath = options["kapt.kotlin.generated"]
    val path = generatedPath
            ?.replace("(.*)tmp(/kapt/debug/)kotlinGenerated".toRegex(), "$1generated/source$2")
    fileSpec.writeTo(File(path, "${fileSpec.name}.kt"))
}