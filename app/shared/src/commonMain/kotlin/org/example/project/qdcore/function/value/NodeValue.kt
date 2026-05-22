package org.example.project.qdcore.function.value

import org.example.project.qdcore.ast.MarkdownContent
import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.function.expression.Expression
import org.example.project.qdcore.function.expression.visitor.ExpressionVisitor
import org.example.project.qdcore.function.value.output.OutputValueVisitor

/**
 * An immutable [Node] [Value].
 */
data class NodeValue(
    override val unwrappedValue: Node,
) : OutputValue<Node>,
    Expression,
    AdaptableValue<MarkdownContentValue> {
    override fun <O> accept(visitor: OutputValueVisitor<O>): O = visitor.visit(this)

    override fun <T> accept(visitor: ExpressionVisitor<T>): T = visitor.visit(this)

    override fun adapt(): MarkdownContentValue = MarkdownContentValue(MarkdownContent(listOf(unwrappedValue)))
}

/**
 * @return [this] node wrapped into a [NodeValue]
 */
fun Node.wrappedAsValue() = NodeValue(this)
