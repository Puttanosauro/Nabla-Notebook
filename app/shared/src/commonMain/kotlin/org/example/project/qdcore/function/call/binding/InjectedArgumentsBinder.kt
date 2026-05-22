package org.example.project.qdcore.function.call.binding

import org.example.project.qdcore.function.FunctionParameter
import org.example.project.qdcore.function.call.FunctionCall
import org.example.project.qdcore.function.call.FunctionCallArgument
import org.example.project.qdcore.function.reflect.InjectedValue

/**
 * Builder of bindings for the injected argument subset of a function call.
 * @param call function call to bind arguments for
 * @see FunctionParameter.isInjected
 */
class InjectedArgumentsBinder(
    private val call: FunctionCall<*>,
) : ArgumentsBinder {
    override fun createBindings(parameters: List<FunctionParameter<*>>) =
        parameters.associateWith {
            val value = InjectedValue.fromType(it.type, call)
            FunctionCallArgument(value)
        }
}
