package org.example.project.qdcore.flavor.quarkdown

import org.example.project.qdcore.flavor.LexerFactory
import org.example.project.qdcore.flavor.MarkdownFlavor
import org.example.project.qdcore.flavor.ParserFactory
import org.example.project.qdcore.flavor.RendererFactory
import org.example.project.qdcore.flavor.TreeIteratorFactory

/**
 * [org.example.project.qdcore.flavor.base.BaseMarkdownFlavor] extension with, in addition:
 * - Functions
 * - Math blocks
 * - Code span additional content
 * - Image labels
 * - Table of contents
 *
 * And more.
 */
object QuarkdownFlavor : MarkdownFlavor {
    override val lexerFactory: LexerFactory = QuarkdownLexerFactory
    override val parserFactory: ParserFactory = QuarkdownParserFactory()
    override val rendererFactory: RendererFactory = QuarkdownRendererFactory()
    override val treeIteratorFactory: TreeIteratorFactory = QuarkdownTreeIteratorFactory()
}
