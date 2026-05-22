package org.example.project.qdcore.flavor.base

import org.example.project.qdcore.flavor.LexerFactory
import org.example.project.qdcore.flavor.MarkdownFlavor
import org.example.project.qdcore.flavor.ParserFactory
import org.example.project.qdcore.flavor.RendererFactory
import org.example.project.qdcore.flavor.TreeIteratorFactory

/**
 * The vanilla [CommonMark](https://spec.commonmark.org) Markdown with several [GFM](https://github.github.com/gfm) features and extensions.
 */
object BaseMarkdownFlavor : MarkdownFlavor {
    override val lexerFactory: LexerFactory = BaseMarkdownLexerFactory
    override val parserFactory: ParserFactory = BaseMarkdownParserFactory()
    override val rendererFactory: RendererFactory = BaseMarkdownRendererFactory
    override val treeIteratorFactory: TreeIteratorFactory = BaseMarkdownTreeIteratorFactory()
}
