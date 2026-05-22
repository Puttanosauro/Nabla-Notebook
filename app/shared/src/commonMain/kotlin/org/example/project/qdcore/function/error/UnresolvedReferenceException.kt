package org.example.project.qdcore.function.error

import org.example.project.qdcore.UNRESOLVED_REFERENCE_EXIT_CODE
import org.example.project.qdcore.pipeline.error.PipelineException

/**
 * An exception thrown when a function call does not reference any registered function declaration.
 * @param symbol function name
 */
class UnresolvedReferenceException(
    symbol: String,
) : PipelineException("Unresolved reference: $symbol", UNRESOLVED_REFERENCE_EXIT_CODE)
