package org.example.project.qdcore.function.error

import org.example.project.qdcore.ast.InlineContent
import org.example.project.qdcore.function.Function
import org.example.project.qdcore.pipeline.error.PipelineException

/**
 * A [PipelineException] thrown when an error related to a function or function call occurs.
 * @param richMessage formatted message to display
 * @param function function the error is related to
 */
open class FunctionException(
    richMessage: InlineContent,
    code: Int,
    val function: Function<*>,
) : PipelineException(richMessage, code)
