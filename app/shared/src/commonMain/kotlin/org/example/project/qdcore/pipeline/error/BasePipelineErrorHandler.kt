package org.example.project.qdcore.pipeline.error

import org.example.project.qdcore.function.Function
import org.example.project.qdcore.log.Log

/**
 * Simple pipeline error handler that logs the error message.
 */
class BasePipelineErrorHandler : PipelineErrorHandler {
    override fun <T> handle(
        error: Throwable,
        sourceFunction: Function<*>?,
        action: () -> T,
    ): T {
        val message = error.message ?: "Unknown error"
        Log.error(message)
        return action()
    }
}
