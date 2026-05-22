package org.example.project.qdcore.flavor.base

import org.example.project.qdcore.flavor.RendererFactory
import org.example.project.qdcore.flavor.RendererFactoryVisitor

/**
 * [BaseMarkdownFlavor] renderer factory.
 */
data object BaseMarkdownRendererFactory : RendererFactory {
    override fun <T> accept(visitor: RendererFactoryVisitor<T>): T = visitor.visit(this)
}
