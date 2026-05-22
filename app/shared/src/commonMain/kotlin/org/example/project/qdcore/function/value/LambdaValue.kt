package org.example.project.qdcore.function.value

import org.example.project.qdcore.function.expression.visitor.ExpressionVisitor
import org.example.project.qdcore.function.value.data.Lambda

/**
 * A [Value] that wraps an action of variable parameter count.
 */
data class LambdaValue(
    override val unwrappedValue: Lambda,
) : InputValue<Lambda> {
    override fun <T> accept(visitor: ExpressionVisitor<T>): T = visitor.visit(this)
}
