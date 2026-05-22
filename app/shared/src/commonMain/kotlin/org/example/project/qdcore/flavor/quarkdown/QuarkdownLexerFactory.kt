package org.example.project.qdcore.flavor.quarkdown

import org.example.project.qdcore.flavor.InlineLexerVariant
import org.example.project.qdcore.flavor.LexerFactory
import org.example.project.qdcore.flavor.base.BaseMarkdownLexerFactory
import org.example.project.qdcore.lexer.Lexer
import org.example.project.qdcore.lexer.patterns.FunctionCallPatterns
import org.example.project.qdcore.lexer.patterns.QuarkdownBlockTokenRegexPatterns
import org.example.project.qdcore.lexer.patterns.QuarkdownInlineTokenRegexPatterns
import org.example.project.qdcore.lexer.regex.StandardRegexLexer
import org.example.project.qdcore.lexer.tokens.PlainTextToken

/**
 * [QuarkdownFlavor] lexer factory.
 */
object QuarkdownLexerFactory : LexerFactory {
    private val blockPatterns = QuarkdownBlockTokenRegexPatterns()
    private val inlinePatterns = QuarkdownInlineTokenRegexPatterns()
    private val functionCallPatterns = FunctionCallPatterns()
    private val base = BaseMarkdownLexerFactory

    /**
     * Inserts patterns of Quarkdown's inline extensions into the base inline lexer (produced by [BaseMarkdownLexerFactory]).
     * @return a copy of the base inline lexer also containing Quarkdown's inline extensions.
     */
    private fun StandardRegexLexer.insertInlineExtensions(): Lexer {
        // New inline patterns introduced by this flavor on top of the base patterns.
        val inlineExtensions =
            with(inlinePatterns) {
                listOf(
                    inlineFunctionCall,
                    inlineMath,
                    *textReplacements.toTypedArray(),
                )
            }

        // The last pattern is the critical content one, which should always be last.
        return this.updatePatterns { patterns ->
            patterns.dropLast(1) + inlineExtensions + patterns.last()
        }
    }

    override fun newBlockLexer(source: CharSequence): Lexer =
        with(blockPatterns) {
            StandardRegexLexer(
                source,
                listOf(
                    comment,
                    functionCall,
                    blockQuote,
                    blockCode,
                    footnoteDefinition,
                    linkDefinition,
                    fencesCode,
                    multilineMath,
                    onelineMath,
                    heading,
                    horizontalRule,
                    pageBreak,
                    setextHeading,
                    table,
                    unorderedList,
                    orderedList,
                    newline,
                    paragraph,
                    blockText,
                ),
            )
        }

    override fun newListLexer(source: CharSequence): Lexer = base.newListLexer(source)

    override fun newInlineLexer(
        source: CharSequence,
        variant: InlineLexerVariant,
    ): Lexer = base.newInlineLexer(source, variant).insertInlineExtensions()

    override fun newExpressionLexer(
        source: CharSequence,
        allowBlockFunctionCalls: Boolean,
    ): Lexer =
        with(inlinePatterns) {
            // A function call argument contains textual content (string/number/...)
            // and possibly other nested function calls.
            StandardRegexLexer(
                source,
                if (allowBlockFunctionCalls) {
                    listOf(
                        escape,
                        functionCallPatterns.expressionBlockFunctionCall,
                        inlineFunctionCall,
                    )
                } else {
                    listOf(
                        escape,
                        inlineFunctionCall,
                    )
                },
                fillTokenType = ::PlainTextToken,
            )
        }

    /**
     * Creates a lexer for inline function calls.
     * This lexer is mainly used for function call completion and highlighting in the LSP.
     * @param source the source text to tokenize
     * @return a lexer that recognizes inline function calls
     *         (block arguments are not included, as they are part of block function calls)
     */
    fun newInlineFunctionCallLexer(source: CharSequence): Lexer =
        StandardRegexLexer(
            source,
            listOf(inlinePatterns.inlineFunctionCall),
        )
}
