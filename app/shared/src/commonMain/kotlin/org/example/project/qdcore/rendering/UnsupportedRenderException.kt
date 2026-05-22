package org.example.project.qdcore.rendering

import org.example.project.qdcore.ast.Node
import kotlin.reflect.KClass

/**
 * An exception thrown when a [org.example.project.qdcore.rendering.NodeRenderer] tries rendering a node which is unsupported by its flavor.
 * @param elementClass class of the element whose rendering was attempted
 */
class UnsupportedRenderException(
    elementClass: KClass<*>,
) : UnsupportedOperationException("${elementClass.simpleName} rendering is not supported by this flavor.") {
    /**
     * @param node node whose rendering was attempted
     */
    constructor(node: Node) : this(node::class)
}
