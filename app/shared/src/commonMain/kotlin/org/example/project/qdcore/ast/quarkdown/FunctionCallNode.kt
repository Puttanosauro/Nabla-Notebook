package org.example.project.qdcore.ast.quarkdown

import org.example.project.qdcore.ast.NestableNode
import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.ast.attributes.error.ErrorCapableNode
import org.example.project.qdcore.context.Context
import org.example.project.qdcore.function.call.FunctionCallArgument
import org.example.project.qdcore.pipeline.error.PipelineErrorHandler
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * A call to a function.
 * The call is executed after parsing, and its output is stored in its mutable [children].
 * @param context context this node lies in, which is where symbols will be loaded from upon execution
 * @param name name of the function to call
 * @param arguments arguments to call the function with
 * @param isBlock whether this function call is an isolated block (opposite: inline)
 * @param sourceText if available, the source code of the whole function call
 * @param sourceRange if available, the range of the function call in the source code
 */
class FunctionCallNode(
    val context: Context,
    val name: String,
    val arguments: List<FunctionCallArgument>,
    val isBlock: Boolean,
    val sourceText: CharSequence? = null,
    val sourceRange: IntRange? = null,
) : NestableNode,
    ErrorCapableNode {
    override val children: MutableList<Node> = mutableListOf()

    override var error: Pair<Throwable, PipelineErrorHandler>? = null

    override fun <T> acceptOnSuccess(visitor: NodeVisitor<T>): T = visitor.visit(this)
}
