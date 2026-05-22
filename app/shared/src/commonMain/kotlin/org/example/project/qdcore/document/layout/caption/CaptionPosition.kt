package org.example.project.qdcore.document.layout.caption

import org.example.project.qdcore.rendering.representable.RenderRepresentable
import org.example.project.qdcore.rendering.representable.RenderRepresentableVisitor

/**
 * Possible positions of captions, relative to the element they describe.
 * @see CaptionPositionInfo
 */
enum class CaptionPosition : RenderRepresentable {
    TOP,
    BOTTOM,
    ;

    override fun <T> accept(visitor: RenderRepresentableVisitor<T>): T = visitor.visit(this)
}
