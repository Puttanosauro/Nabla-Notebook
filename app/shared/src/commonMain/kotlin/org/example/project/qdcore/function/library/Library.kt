package org.example.project.qdcore.function.library

import org.example.project.qdcore.context.Context
import org.example.project.qdcore.function.Function
import org.example.project.qdcore.function.value.OutputValue
import org.example.project.qdcore.pipeline.PipelineHooks

/**
 * A bundle of functions that can be called from a Quarkdown source.
 * @param name name of the library
 * @param functions functions the library makes available to call
 * @param onLoad optional action to run when the library is loaded in a context. Returns an optional value to be used as the result of loading the library
 * @param hooks optional actions to run after each stage of a pipeline where this library is registered in has been completed
 */
data class Library(
    val name: String,
    val functions: Set<Function<*>>,
    val onLoad: ((Context) -> OutputValue<*>)? = null,
    val hooks: PipelineHooks? = null,
) {
    /**
     * @return a copy of this library with the given pipeline hooks attached
     */
    fun withHooks(hooks: PipelineHooks) = copy(hooks = hooks)
}
