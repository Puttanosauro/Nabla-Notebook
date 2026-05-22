package org.example.project.qdcore.flavor

/**
 * A flavor of Markdown. Each flavor consists of its own rules and extensions.
 * @see org.example.project.qdcore.flavor.base.BaseMarkdownFlavor
 * @see org.example.project.qdcore.flavor.quarkdown.QuarkdownFlavor
 */
interface MarkdownFlavor {
    /**
     * The supplier of new lexer instances to tokenize various scopes from a raw string input.
     */
    val lexerFactory: LexerFactory

    /**
     * The supplier of new parser instances to obtain processed nodes from raw tokens.
     */
    val parserFactory: ParserFactory

    /**
     * The supplier of new tree iterator instances to traverse the node tree.
     */
    val treeIteratorFactory: TreeIteratorFactory

    /**
     * The supplier of new renderer instances to convert processed nodes to output content.
     */
    val rendererFactory: RendererFactory
}
