package org.example.project.qdcore.function.value.output.node

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.context.Context
import org.example.project.qdcore.function.value.output.OutputValueVisitor
import org.example.project.qdcore.function.value.output.OutputValueVisitorFactory

/**
 * A factory that produces [OutputValueVisitor]s that map function output values
 * into [Node]s that can be appended to the AST.
 * @param context current context
 */
class NodeOutputValueVisitorFactory(
    private val context: Context,
) : OutputValueVisitorFactory<Node> {
    /**
     * @return a visitor that maps the output of a block function call into a block [Node]
     */
    override fun block(): OutputValueVisitor<Node> = BlockNodeOutputValueVisitor(context)

    /**
     * @return a visitor that maps the output of an inline function call into an inline [Node]
     */
    override fun inline(): OutputValueVisitor<Node> = InlineNodeOutputValueVisitor(context)
}
