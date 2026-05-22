package org.example.project.qdcore.document.layout.caption

import com.quarkdown.amber.annotations.Mergeable

/**
 * Immutable information about the position of captions of [org.example.project.qdcore.ast.quarkdown.CaptionableNode] nodes in a document.
 * @param default default relative position of captions
 * @param figures position of captions for [org.example.project.qdcore.ast.quarkdown.block.Figure], if different from the default
 * @param tables position of captions for [org.example.project.qdcore.ast.base.block.Table], if different from the default
 * @param codeBlocks position of captions for [org.example.project.qdcore.ast.base.block.Code], if different from the default
 */
@Mergeable
data class CaptionPositionInfo(
    val default: CaptionPosition = CaptionPosition.BOTTOM,
    val figures: CaptionPosition? = null,
    val tables: CaptionPosition? = null,
    val codeBlocks: CaptionPosition? = null,
) {
    /**
     * @return the value of [property] if it is not `null`, otherwise the [default] value.
     */
    fun getOrDefault(property: CaptionPositionInfo.() -> CaptionPosition?): CaptionPosition = property() ?: default
}
