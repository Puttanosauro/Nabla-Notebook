package org.example.project.qdcore.flavor.quarkdown

import org.example.project.qdcore.flavor.RendererFactory
import org.example.project.qdcore.flavor.RendererFactoryVisitor

/**
 * [QuarkdownRendererFactory] renderer factory.
 */
class QuarkdownRendererFactory : RendererFactory {
    override fun <T> accept(visitor: RendererFactoryVisitor<T>): T = visitor.visit(this)
}
