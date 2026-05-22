package org.example.project.qdcore.ast.base.inline

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * An immutable checkbox that is either checked or unchecked.
 * @param isChecked whether the checkbox is checked
 * @see org.example.project.qdcore.ast.base.block.TaskListItem
 */
class CheckBox(
    val isChecked: Boolean,
) : Node {
    override fun <T> accept(visitor: NodeVisitor<T>): T = visitor.visit(this)
}
