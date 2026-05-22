package org.example.project.qdcore.document.slides

import org.example.project.qdcore.rendering.representable.RenderRepresentable
import org.example.project.qdcore.rendering.representable.RenderRepresentableVisitor

/**
 * An animated transition between two slides.
 * @param style transition type
 * @param speed speed of the transition
 */
data class Transition(
    val style: Style,
    val speed: Speed = Speed.DEFAULT,
) {
    /**
     * Transition types.
     */
    enum class Style : RenderRepresentable {
        NONE,
        FADE,
        SLIDE,
        ZOOM,
        ;

        override fun <T> accept(visitor: RenderRepresentableVisitor<T>) = visitor.visit(this)
    }

    /**
     * Transition speeds.
     */
    enum class Speed : RenderRepresentable {
        DEFAULT,
        FAST,
        SLOW,
        ;

        override fun <T> accept(visitor: RenderRepresentableVisitor<T>) = visitor.visit(this)
    }
}
