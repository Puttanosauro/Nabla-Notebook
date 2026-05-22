package org.example.project.qdcore.function.value.factory

import org.example.project.qdcore.ILLEGAL_TYPE_CONVERSION_EXIT_CODE
import org.example.project.qdcore.pipeline.error.PipelineException

/**
 * An exception thrown when a dynamic value cannot be converted to a static type via a [ValueFactory] method.
 * @param raw raw value that could not be converted
 */
class IllegalRawValueException(
    message: String,
    raw: Any,
) : PipelineException(
        "$message: $raw",
        ILLEGAL_TYPE_CONVERSION_EXIT_CODE,
    )
