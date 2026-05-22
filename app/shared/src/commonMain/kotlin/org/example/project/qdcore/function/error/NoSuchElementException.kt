package org.example.project.qdcore.function.error

import org.example.project.qdcore.NO_SUCH_ELEMENT_EXIT_CODE
import org.example.project.qdcore.function.quarkdownName
import org.example.project.qdcore.pipeline.error.PipelineException

/**
 * Exception thrown when an element (e.g. an enum value from a Quarkdown function argument)
 * does not exist among elements of a look-up table.
 */
class NoSuchElementException(
    element: Any,
    values: Iterable<*>,
) : PipelineException("No such element '$element' among values $values", NO_SUCH_ELEMENT_EXIT_CODE) {
    constructor(element: Any, values: Array<Enum<*>>) : this(element, values.map { it.quarkdownName })
}
