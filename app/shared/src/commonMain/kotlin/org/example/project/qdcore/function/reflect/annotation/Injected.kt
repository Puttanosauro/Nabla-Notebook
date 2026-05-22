package org.example.project.qdcore.function.reflect.annotation

/**
 * When a library function parameter is annotated with `@Injected`, its value is not supplied by a function call
 * but rather automatically injected by [org.example.project.qdcore.function.call.binding.InjectedArgumentsBinder].
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Injected
