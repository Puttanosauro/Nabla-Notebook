package org.example.project.qdcore.function.library

import org.example.project.qdcore.context.Context
import org.example.project.qdcore.context.MutableContext
import org.example.project.qdcore.function.value.OutputValue

/**
 * Component that is responsible for registering libraries in a pipeline's [Context],
 * in order to be looked up later.
 * @param context context to push libraries to
 */
class LibraryRegistrant(
    private val context: MutableContext,
) {
    /**
     * Registers a new single library, allowing it to be looked up by functions
     * and its [Library.onLoad] action is executed.
     * @param library library to register
     */
    fun register(library: Library): OutputValue<*>? {
        context.libraries += library
        return library.onLoad?.invoke(context)
    }

    /**
     * Registers a new set of libraries, allowing them to be looked up by functions.
     * and their [Library.onLoad] action is executed.
     * @param libraries libraries to register
     */
    fun registerAll(libraries: Collection<Library>) {
        libraries.forEach(::register)
    }
}
