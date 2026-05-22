package org.example.project.qdcore.function.library.loader

import org.example.project.qdcore.function.library.Library
import org.example.project.qdcore.function.reflect.KFunctionAdapter
import org.example.project.qdcore.function.value.OutputValue
import kotlin.reflect.KFunction

/**
 * A Quarkdown function that can be exported via a [FunctionLibraryLoader].
 */
typealias ExportableFunction = KFunction<OutputValue<*>>

/**
 * Creates a library from a single Kotlin function.
 * @see KFunctionAdapter
 */
class FunctionLibraryLoader : LibraryLoader<ExportableFunction> {
    override fun load(source: ExportableFunction) = Library(source.name, setOf(KFunctionAdapter(source)))
}
