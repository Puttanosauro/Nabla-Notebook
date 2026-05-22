package org.example.project.qdcore.document.numbering

import com.quarkdown.amber.annotations.Mergeable
import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.ast.base.block.FootnoteDefinition
import org.example.project.qdcore.ast.base.block.Heading
import org.example.project.qdcore.ast.base.block.Table
import org.example.project.qdcore.ast.base.inline.ReferenceFootnote
import org.example.project.qdcore.ast.quarkdown.block.Math

/**
 * An immutable group of [NumberingFormat]s for different types of elements ([Node]s) in a document.
 * @param headings format for [Heading]s
 * @param figures format for [Figure]s
 * @param tables format for [Table]s
 * @param math format for [Math] blocks
 * @param codeBlocks format for [Code] blocks
 * @param footnotes format for [FootnoteDefinition] and [ReferenceFootnote]s
 * @param extra extra, dynamic formats for custom elements (e.g. [org.example.project.qdcore.ast.quarkdown.block.Numbered])
 */
@Mergeable
data class DocumentNumbering(
    val headings: NumberingFormat? = null,
    val figures: NumberingFormat? = null,
    val tables: NumberingFormat? = null,
    val math: NumberingFormat? = null,
    val codeBlocks: NumberingFormat? = null,
    val footnotes: NumberingFormat? = null,
    val extra: Map<String, NumberingFormat> = emptyMap(),
)
