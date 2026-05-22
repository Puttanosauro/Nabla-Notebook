package org.example.project.qdcore.function.reflect

import org.example.project.qdcore.function.Function
import org.example.project.qdcore.function.FunctionParameter
import org.example.project.qdcore.function.call.FunctionCall
import org.example.project.qdcore.function.call.binding.ArgumentBindings
import org.example.project.qdcore.function.call.validate.FunctionCallValidator
import org.example.project.qdcore.function.error.FunctionCallRuntimeException
import org.example.project.qdcore.function.reflect.annotation.Injected
import org.example.project.qdcore.function.reflect.annotation.Name
import org.example.project.qdcore.function.reflect.annotation.NoAutoArgumentUnwrapping
import org.example.project.qdcore.function.reflect.annotation.NotForDocumentType
import org.example.project.qdcore.function.reflect.annotation.OnlyForDocumentType
import org.example.project.qdcore.function.reflect.annotation.toValidator
import org.example.project.qdcore.function.value.InputValue
import org.example.project.qdcore.function.value.None
import org.example.project.qdcore.function.value.OutputValue
import org.example.project.qdcore.log.Log
import org.example.project.qdcore.pipeline.error.PipelineException
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

/**
 * A Quarkdown [Function] adapted from a regular Kotlin [KFunction].
 *
 * Reflection metadata is read eagerly once at construction and cached as immutable state,
 * so each call site (name lookup, parameter introspection, validator selection, invocation)
 * is a direct field read rather than a fresh reflection scan.
 *
 * @param function Kotlin function to adapt
 */
class KFunctionAdapter<T : OutputValue<*>>(
    function: KFunction<T>,
) : Function<T> {
    /** Cached KParameter array, indexed by parameter position, used by [invoke] to avoid repeated reflection lookups per call. */
    private val kParameters: Array<KParameter> = function.parameters.toTypedArray()

    /**
     * If the [Name] annotation is present on [function], the Quarkdown function name is set from there.
     * Otherwise, it is [function]'s original name.
     */
    override val name: String =
        function.findAnnotation<Name>()?.name ?: function.name

    @Suppress("UNCHECKED_CAST")
    override val parameters: List<FunctionParameter<*>> =
        kParameters.map {
            FunctionParameter(
                // If @Name is present, a custom name is set.
                name = it.findAnnotation<Name>()?.name ?: it.name ?: "<unnamed parameter>",
                type = it.type.classifier as KClass<out InputValue<T>>,
                index = it.index,
                isOptional = it.isOptional,
                isInjected = it.hasAnnotation<Injected>(),
                isNullable = it.type.isMarkedNullable,
            )
        }

    override val validators: List<FunctionCallValidator<T>> =
        buildList {
            function.findAnnotation<OnlyForDocumentType>()?.toValidator<T>()?.let(::add)
            function.findAnnotation<NotForDocumentType>()?.toValidator<T>()?.let(::add)
        }

    override val invoke: (ArgumentBindings, FunctionCall<T>) -> T = { bindings, call ->
        val args =
            bindings.asSequence().associate { (parameter, argument) ->
                // Corresponding KParameter, looked up from the cached array.
                val param = kParameters[parameter.index]

                // The argument is unwrapped unless the value class specifies not to.
                // An example of a disabled unwrapping is DynamicValue, which is used to pass dynamically typed values as-is.
                val arg =
                    argument.value.let {
                        if (it::class.hasAnnotation<NoAutoArgumentUnwrapping>()) it else it.unwrappedValue
                    }

                // Quarkdown's None becomes Kotlin's null for nullable parameters.
                param to arg.takeUnless { arg is None && param.type.isMarkedNullable }
            }

        // Call the KFunction.
        try {
            function.callBy(args)
        } catch (e: InvocationTargetException) {
            // Exceptions thrown within the called function are converted to Quarkdown exceptions
            // and handled accordingly by the pipeline's function expander component.
            Log.debug("(expected, received): " + args.map { it.key.type to it.value })

            // If the exception comes from a nested function call, it is rethrown to go up the stack.
            throw e.targetException as? PipelineException
                ?: FunctionCallRuntimeException(call, e.targetException)
        }
    }
}
