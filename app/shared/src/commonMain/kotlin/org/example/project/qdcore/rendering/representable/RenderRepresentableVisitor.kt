package org.example.project.qdcore.rendering.representable

import org.example.project.qdcore.ast.base.block.BlockQuote
import org.example.project.qdcore.ast.base.block.Table
import org.example.project.qdcore.ast.quarkdown.block.Box
import org.example.project.qdcore.ast.quarkdown.block.Clipped
import org.example.project.qdcore.ast.quarkdown.block.Container
import org.example.project.qdcore.ast.quarkdown.block.NavigationContainer
import org.example.project.qdcore.ast.quarkdown.block.SlidesFragment
import org.example.project.qdcore.ast.quarkdown.block.Stacked
import org.example.project.qdcore.ast.quarkdown.inline.TextTransformData
import org.example.project.qdcore.document.layout.caption.CaptionPosition
import org.example.project.qdcore.document.layout.page.PageMarginPosition
import org.example.project.qdcore.document.layout.page.PageSide
import org.example.project.qdcore.document.size.Size
import org.example.project.qdcore.document.size.Sizes
import org.example.project.qdcore.document.slides.Transition
import org.example.project.qdcore.misc.color.Color

/**
 * Visitor that produces representations of each [RenderRepresentable] subtype
 * suitable for the final rendered document.
 */
interface RenderRepresentableVisitor<T> {
    fun visit(color: Color): T

    fun visit(size: Size): T

    fun visit(sizes: Sizes): T

    fun visit(alignment: Table.Alignment): T

    fun visit(position: CaptionPosition): T

    fun visit(borderStyle: Container.BorderStyle): T

    fun visit(alignment: Container.Alignment): T

    fun visit(alignment: Container.TextAlignment): T

    fun visit(alignment: Container.FloatAlignment): T

    fun visit(stackLayout: Stacked.Layout): T

    fun visit(alignment: Stacked.MainAxisAlignment): T

    fun visit(alignment: Stacked.CrossAxisAlignment): T

    fun visit(clip: Clipped.Clip): T

    fun visit(quoteType: BlockQuote.Type): T

    fun visit(boxType: Box.Type): T

    fun visit(navigationRole: NavigationContainer.Role): T

    fun visit(position: PageMarginPosition): T

    fun visit(transition: Transition.Style): T

    fun visit(speed: Transition.Speed): T

    fun visit(behavior: SlidesFragment.Behavior): T

    fun visit(size: TextTransformData.Size): T

    fun visit(weight: TextTransformData.Weight): T

    fun visit(style: TextTransformData.Style): T

    fun visit(decoration: TextTransformData.Decoration): T

    fun visit(case: TextTransformData.Case): T

    fun visit(variant: TextTransformData.Variant): T

    fun visit(script: TextTransformData.Script): T

    fun visit(pageSide: PageSide): T
}
