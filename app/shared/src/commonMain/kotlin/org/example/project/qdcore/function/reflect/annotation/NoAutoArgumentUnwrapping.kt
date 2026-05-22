package org.example.project.qdcore.function.reflect.annotation

import org.example.project.qdcore.function.reflect.KFunctionAdapter
import org.example.project.qdcore.function.value.Value

/**
 * When invoking a function via [KFunctionAdapter], [Value] arguments are automatically unwrapped to their raw value,
 * unless this annotation is present on the [Value] subclass.
 */
@Target(AnnotationTarget.CLASS)
annotation class NoAutoArgumentUnwrapping
