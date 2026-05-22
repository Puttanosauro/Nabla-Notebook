package org.example.project.qdcore.function.value

import org.example.project.qdcore.function.expression.visitor.ExpressionVisitor
import org.example.project.qdcore.function.value.output.OutputValueVisitor

/**
 * An immutable [Value] that contains other values of the same type. The kind of ordering is not relevant.
 * When exporting to a node via [org.example.project.qdcore.function.value.output.node.NodeOutputValueVisitor],
 * this collection is simply converted to a group of nodes.
 * @param T the element type of the list
 */
data class GeneralCollectionValue<T : OutputValue<*>>(
    override val unwrappedValue: Iterable<T>,
) : IterableValue<T> {
    override fun <T> accept(visitor: ExpressionVisitor<T>): T = visitor.visit(this)

    override fun <O> accept(visitor: OutputValueVisitor<O>): O = visitor.visit(this)
}
