package org.example.project.qdcore.flavor

import org.example.project.qdcore.ast.iterator.AstIterator
import org.example.project.qdcore.context.MutableContext

/**
 * Provider of tree iterators.
 * A tree iterator is responsible for traversing the AST and performing operations
 * such as registrations, table of contents generation, etc.
 */
interface TreeIteratorFactory {
    /**
     * @param context the context of the document
     * @return the default tree iterator
     */
    fun default(context: MutableContext): AstIterator
}
