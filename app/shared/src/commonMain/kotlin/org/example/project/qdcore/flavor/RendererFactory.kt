package org.example.project.qdcore.flavor

import org.example.project.qdcore.flavor.base.BaseMarkdownRendererFactory
import org.example.project.qdcore.flavor.quarkdown.QuarkdownRendererFactory

/**
 * Provider of rendering strategies.
 * This factory is populated by extensions provided by external modules, such as `quarkdown-html`.
 *
 * See [com.quarkdown.rendering.html.extension.html] for an example of a renderer extension.
 */
interface RendererFactory {
    /**
     * Accepts a visitor to this renderer factory.
     * @param visitor the visitor to accept
     */
    fun <T> accept(visitor: RendererFactoryVisitor<T>): T
}

/**
 * Visitor for renderer factories of different [MarkdownFlavor].
 * @param T the type of the result of the visit operation
 */
interface RendererFactoryVisitor<T> {
    fun visit(factory: BaseMarkdownRendererFactory): T

    fun visit(factory: QuarkdownRendererFactory): T
}
