package org.example.project.qdcore.function.error

import org.example.project.qdcore.function.FunctionParameter
import org.example.project.qdcore.function.call.FunctionCall
import org.example.project.qdcore.function.call.FunctionCallArgument
import org.example.project.qdcore.function.call.asString

/**
 * An exception thrown if a function parameter is bound more than once in a function call.
 * @param call the invalid call
 * @param parameter the parameter that was attempted to be bound again
 * @param overridingArgument the argument that was attempted to be bound to the already bound parameter
 */
class ParameterAlreadyBoundException(
    call: FunctionCall<*>,
    parameter: FunctionParameter<*>,
    overridingArgument: FunctionCallArgument,
) : InvalidFunctionCallException(
        call,
        reason = "parameter '${parameter.name}' is already bound, but was attempted to be bound again to ${overridingArgument.asString()}",
    )
