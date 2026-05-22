package org.example.project.qdcore.ast.quarkdown.block

import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.visitor.node.NodeVisitor

/**
 * A graph representing the relationships between [org.example.project.qdcore.document.sub.Subdocument]s
 * within the document, stored in [org.example.project.qdcore.context.Context.sharedSubdocumentsData].
 */
class SubdocumentGraph : Node {
    override fun <T> accept(visitor: NodeVisitor<T>): T = visitor.visit(this)
}
