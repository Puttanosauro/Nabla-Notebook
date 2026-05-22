package org.example.project.qdcore.function.call.validate

import org.example.project.qdcore.function.call.FunctionCall
import org.example.project.qdcore.function.value.OutputValue

/**
 * Validator of a function call.
 * @param T output type of the function
 */
interface FunctionCallValidator<T : OutputValue<*>> {
    /**
     * Validates a function call.
     * If a condition is not met, an exception should be thrown (ideally, a [FunctionException] or subclass).
     * Validation should not have any side effects.
     */
    fun validate(call: FunctionCall<T>)
}
