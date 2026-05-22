package org.example.project.qdcore.visitor.node

import org.example.project.qdcore.ast.AstRoot
import org.example.project.qdcore.ast.base.block.BlankNode
import org.example.project.qdcore.ast.base.block.BlockQuote
import org.example.project.qdcore.ast.base.block.Code
import org.example.project.qdcore.ast.base.block.FootnoteDefinition
import org.example.project.qdcore.ast.base.block.Heading
import org.example.project.qdcore.ast.base.block.HorizontalRule
import org.example.project.qdcore.ast.base.block.Html
import org.example.project.qdcore.ast.base.block.LinkDefinition
import org.example.project.qdcore.ast.base.block.Newline
import org.example.project.qdcore.ast.base.block.Paragraph
import org.example.project.qdcore.ast.base.block.Table
import org.example.project.qdcore.ast.base.block.list.ListItem
import org.example.project.qdcore.ast.base.block.list.OrderedList
import org.example.project.qdcore.ast.base.block.list.UnorderedList
import org.example.project.qdcore.ast.base.inline.CheckBox
import org.example.project.qdcore.ast.base.inline.CodeSpan
import org.example.project.qdcore.ast.base.inline.Comment
import org.example.project.qdcore.ast.base.inline.CriticalContent
import org.example.project.qdcore.ast.base.inline.Emphasis
import org.example.project.qdcore.ast.base.inline.Image
import org.example.project.qdcore.ast.base.inline.LineBreak
import org.example.project.qdcore.ast.base.inline.Link
import org.example.project.qdcore.ast.base.inline.ReferenceFootnote
import org.example.project.qdcore.ast.base.inline.ReferenceImage
import org.example.project.qdcore.ast.base.inline.ReferenceLink
import org.example.project.qdcore.ast.base.inline.Strikethrough
import org.example.project.qdcore.ast.base.inline.Strong
import org.example.project.qdcore.ast.base.inline.StrongEmphasis
import org.example.project.qdcore.ast.base.inline.SubdocumentLink
import org.example.project.qdcore.ast.base.inline.Text
import org.example.project.qdcore.ast.quarkdown.FunctionCallNode
import org.example.project.qdcore.ast.quarkdown.bibliography.BibliographyCitation
import org.example.project.qdcore.ast.quarkdown.bibliography.BibliographyView
import org.example.project.qdcore.ast.quarkdown.block.Box
import org.example.project.qdcore.ast.quarkdown.block.Clipped
import org.example.project.qdcore.ast.quarkdown.block.Collapse
import org.example.project.qdcore.ast.quarkdown.block.Container
import org.example.project.qdcore.ast.quarkdown.block.Figure
import org.example.project.qdcore.ast.quarkdown.block.FileTree
import org.example.project.qdcore.ast.quarkdown.block.Landscape
import org.example.project.qdcore.ast.quarkdown.block.Math
import org.example.project.qdcore.ast.quarkdown.block.MermaidDiagram
import org.example.project.qdcore.ast.quarkdown.block.NavigationContainer
import org.example.project.qdcore.ast.quarkdown.block.Numbered
import org.example.project.qdcore.ast.quarkdown.block.PageBreak
import org.example.project.qdcore.ast.quarkdown.block.SlidesFragment
import org.example.project.qdcore.ast.quarkdown.block.SlidesSpeakerNote
import org.example.project.qdcore.ast.quarkdown.block.Stacked
import org.example.project.qdcore.ast.quarkdown.block.SubdocumentGraph
import org.example.project.qdcore.ast.quarkdown.block.toc.TableOfContentsView
import org.example.project.qdcore.ast.quarkdown.inline.IconImage
import org.example.project.qdcore.ast.quarkdown.inline.InlineCollapse
import org.example.project.qdcore.ast.quarkdown.inline.Keybinding
import org.example.project.qdcore.ast.quarkdown.inline.LastHeading
import org.example.project.qdcore.ast.quarkdown.inline.MathSpan
import org.example.project.qdcore.ast.quarkdown.inline.PageCounter
import org.example.project.qdcore.ast.quarkdown.inline.TextSymbol
import org.example.project.qdcore.ast.quarkdown.inline.TextTransform
import org.example.project.qdcore.ast.quarkdown.inline.Whitespace
import org.example.project.qdcore.ast.quarkdown.invisible.PageMarginContentInitializer
import org.example.project.qdcore.ast.quarkdown.invisible.PageNumberFormatter
import org.example.project.qdcore.ast.quarkdown.invisible.PageNumberReset
import org.example.project.qdcore.ast.quarkdown.invisible.SlidesConfigurationInitializer
import org.example.project.qdcore.ast.quarkdown.reference.CrossReference

/**
 * A visitor for [org.example.project.qdcore.ast.Node]s.
 * @param T output type of the `visit` methods
 */
interface NodeVisitor<T> {
    fun visit(node: AstRoot): T

    // Base block

    fun visit(node: Newline): T

    fun visit(node: Code): T

    fun visit(node: HorizontalRule): T

    fun visit(node: Heading): T

    fun visit(node: LinkDefinition): T

    fun visit(node: FootnoteDefinition): T

    fun visit(node: OrderedList): T

    fun visit(node: UnorderedList): T

    fun visit(node: ListItem): T

    fun visit(node: Html): T

    fun visit(node: Table): T

    fun visit(node: Paragraph): T

    fun visit(node: BlockQuote): T

    fun visit(node: BlankNode): T

    // Base inline

    fun visit(node: Comment): T

    fun visit(node: LineBreak): T

    fun visit(node: CriticalContent): T

    fun visit(node: Link): T

    fun visit(node: ReferenceLink): T

    fun visit(node: SubdocumentLink): T

    fun visit(node: ReferenceFootnote): T

    fun visit(node: Image): T

    fun visit(node: ReferenceImage): T

    fun visit(node: CheckBox): T

    fun visit(node: Text): T

    fun visit(node: TextSymbol): T

    fun visit(node: CodeSpan): T

    fun visit(node: Emphasis): T

    fun visit(node: Strong): T

    fun visit(node: StrongEmphasis): T

    fun visit(node: Strikethrough): T

    // Quarkdown extensions

    fun visit(node: FunctionCallNode): T

    // Quarkdown block

    fun visit(node: Figure<*>): T

    fun visit(node: PageBreak): T

    fun visit(node: Math): T

    fun visit(node: Container): T

    fun visit(node: Stacked): T

    fun visit(node: Numbered): T

    fun visit(node: Landscape): T

    fun visit(node: Clipped): T

    fun visit(node: Box): T

    fun visit(node: Collapse): T

    fun visit(node: Whitespace): T

    fun visit(node: NavigationContainer): T

    fun visit(node: TableOfContentsView): T

    fun visit(node: BibliographyView): T

    fun visit(node: MermaidDiagram): T

    fun visit(node: FileTree): T

    fun visit(node: SubdocumentGraph): T

    // Quarkdown inline

    fun visit(node: MathSpan): T

    fun visit(node: TextTransform): T

    fun visit(node: IconImage): T

    fun visit(node: InlineCollapse): T

    fun visit(node: Keybinding): T

    fun visit(node: PageCounter): T

    fun visit(node: LastHeading): T

    fun visit(node: CrossReference): T

    fun visit(node: BibliographyCitation): T

    fun visit(node: SlidesFragment): T

    fun visit(node: SlidesSpeakerNote): T

    // Quarkdown invisible nodes

    fun visit(node: PageMarginContentInitializer): T

    fun visit(node: PageNumberFormatter): T

    fun visit(node: PageNumberReset): T

    fun visit(node: SlidesConfigurationInitializer): T
}