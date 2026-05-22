package org.example.project.qdcore.flavor.base

import org.example.project.qdcore.context.MutableContext
import org.example.project.qdcore.flavor.ParserFactory
import org.example.project.qdcore.parser.BlockTokenParser
import org.example.project.qdcore.parser.InlineTokenParser

/**
 * [BaseMarkdownFlavor] parser factory.
 */
class BaseMarkdownParserFactory : ParserFactory {
    override fun newBlockParser(context: MutableContext) = BlockTokenParser(context)

    override fun newInlineParser(context: MutableContext) = InlineTokenParser(context)
}
