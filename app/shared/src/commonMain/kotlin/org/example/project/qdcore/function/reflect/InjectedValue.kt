package org.example.project.qdcore.function.reflect

import org.example.project.qdcore.ast.quarkdown.FunctionCallNode
import org.example.project.qdcore.context.Context
import org.example.project.qdcore.function.FunctionParameter
import org.example.project.qdcore.function.call.FunctionCall
import org.example.project.qdcore.function.value.InputValue
import org.example.project.qdcore.function.value.ObjectValue
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * Utility for injected argument values.
 * @see FunctionParameter.isInjected
 * @see org.example.project.qdcore.function.call.binding.InjectedArgumentsBinder
 */
object InjectedValue {
    /**
     * Generates a value to inject to a function parameter that expects a type of [type].
     *
     * Supported types:
     * - [Context]: injects the context of the function call
     * - [FunctionCallNode]: injects the source node of the function call
     * - [FunctionCall]: injects the function call itself
     *
     * @param type type of the target parameter to inject value to.
     * @param call function call to extract injectable data from
     * @return the function-call-ready value to inject
     * @throws IllegalArgumentException if the target type is not injectable
     */
    fun fromType(
        type: KClass<*>,
        call: FunctionCall<*>,
    ): InputValue<*> =
        when {
            type.isSubclassOf(Context::class) -> ObjectValue(call.context)
            type.isSubclassOf(FunctionCallNode::class) -> ObjectValue(call.sourceNode)
            type.isSubclassOf(FunctionCall::class) -> ObjectValue(call)
            else -> throw IllegalArgumentException("Cannot inject a value to type $type")
        }
}
