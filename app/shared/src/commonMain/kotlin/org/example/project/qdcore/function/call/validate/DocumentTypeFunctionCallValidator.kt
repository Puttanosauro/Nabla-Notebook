package org.example.project.qdcore.function.call.validate

import org.example.project.qdcore.document.DocumentType
import org.example.project.qdcore.function.call.FunctionCall
import org.example.project.qdcore.function.error.InvalidFunctionCallException
import org.example.project.qdcore.function.quarkdownName
import org.example.project.qdcore.function.value.OutputValue

/**
 * Validator of a function call that checks if the document the function call lies in is of a certain type.
 * If not, an [InvalidFunctionCallException] is thrown.
 * @param T output type of the function
 * @param allowedTypes allowed document types
 */
class DocumentTypeFunctionCallValidator<T : OutputValue<*>>(
    private val allowedTypes: Iterable<DocumentType>,
) : FunctionCallValidator<T> {
    override fun validate(call: FunctionCall<T>) {
        val type = call.context?.documentInfo?.type ?: return
        if (type in allowedTypes) {
            return
        }

        throw InvalidFunctionCallException(
            call,
            reason =
                "the function was called in a ${type.quarkdownName} document, " +
                    "while it is allowed only in ${allowedTypes.joinToString { it.quarkdownName }}",
            includeArguments = false,
        )
    }
}
