package org.example.project.qdcore.function.value.data

/**
 * A declared parameter of a lambda.
 * (Extracted from upstream Lambda.kt; the full Lambda class arrives with the `function` package port.)
 * @param name name of the parameter
 * @param isOptional whether the corresponding argument can be omitted
 * @param isExplicitlyBody whether the parameter is reserved for the body argument when the lambda
 *                         is registered as a function
 */
data class LambdaParameter(
    val name: String,
    val isOptional: Boolean = false,
    val isExplicitlyBody: Boolean = false,
)
