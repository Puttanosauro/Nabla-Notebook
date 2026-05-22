package org.example.project.qdcore.ast.attributes.error

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.ast.dsl.buildInline
import org.example.project.qdcore.ast.quarkdown.block.Box
import org.example.project.qdcore.function.error.FunctionException
import org.example.project.qdcore.function.error.InvalidFunctionCallException
import org.example.project.qdcore.pipeline.error.PipelineErrorHandler
import org.example.project.qdcore.pipeline.error.PipelineException

/**
 * Converts [this] exception to a renderable [Node], and performs the error handling provided by the [errorHandler] strategy.
 * @param errorHandler strategy to handle the error
 * @return [this] exception as a renderable [Node]
 */
fun Throwable.asNode(errorHandler: PipelineErrorHandler): Node {
    // The function that the error originated from, if any.
    val sourceFunction = (this as? FunctionException)?.function

    return errorHandler.handle(this, sourceFunction) {
        Box.error(
            message =
                when (this) {
                    is PipelineException -> this.richMessage
                    else -> buildInline { text(message ?: this::class.simpleName ?: "Unknown error") }
                },
            title = sourceFunction?.name,
            sourceText =
                (this as? InvalidFunctionCallException)
                    ?.call
                    ?.sourceNode
                    ?.sourceText
                    ?.trim(),
        )
    }
}
