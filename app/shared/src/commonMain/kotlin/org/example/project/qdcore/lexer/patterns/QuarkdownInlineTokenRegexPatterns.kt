package org.example.project.qdcore.lexer.patterns

import org.example.project.qdcore.lexer.regex.RegexBuilder
import org.example.project.qdcore.lexer.regex.pattern.TokenRegexPattern
import org.example.project.qdcore.lexer.tokens.InlineMathToken

/**
 * Regex patterns for [org.example.project.qdcore.flavor.quarkdown.QuarkdownFlavor].
 */
class QuarkdownInlineTokenRegexPatterns : BaseMarkdownInlineTokenRegexPatterns() {
    /**
     * Function name prefixed by '.', followed by a sequence of arguments wrapped in curly braces.
     */
    val inlineFunctionCall by lazy {
        FunctionCallPatterns().inlineFunctionCall
    }

    /**
     * Fenced content within spaced dollar signs on the same line.
     * @see InlineMathToken
     */
    val inlineMath by lazy {
        TokenRegexPattern(
            name = "InlineMath",
            wrap = ::InlineMathToken,
            regex =
                RegexBuilder("(?<=^|\\s|\\W)math(?=$|\\s|\\W)")
                    .withReference("math", PatternHelpers.ONELINE_MATH)
                    .build(),
        )
    }

    /**
     * Patterns for sequences of characters that correspond to text symbols.
     */
    val textReplacements: List<TokenRegexPattern> = TextSymbolReplacement.entries.map { it.toTokenPattern() }
}
