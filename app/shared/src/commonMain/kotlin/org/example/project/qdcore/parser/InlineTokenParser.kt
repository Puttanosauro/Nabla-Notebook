package org.example.project.qdcore.parser

import org.example.project.qdcore.ast.InlineContent
import org.example.project.qdcore.ast.Node
import org.example.project.qdcore.ast.base.LinkNode
import org.example.project.qdcore.ast.base.inline.CodeSpan
import org.example.project.qdcore.ast.base.inline.Comment
import org.example.project.qdcore.ast.base.inline.CriticalContent
import org.example.project.qdcore.ast.base.inline.Emphasis
import org.example.project.qdcore.ast.base.inline.Image
import org.example.project.qdcore.ast.base.inline.LineBreak
import org.example.project.qdcore.ast.base.inline.Link
import org.example.project.qdcore.ast.base.inline.ReferenceDefinitionFootnote
import org.example.project.qdcore.ast.base.inline.ReferenceFootnote
import org.example.project.qdcore.ast.base.inline.ReferenceImage
import org.example.project.qdcore.ast.base.inline.ReferenceLink
import org.example.project.qdcore.ast.base.inline.Strikethrough
import org.example.project.qdcore.ast.base.inline.Strong
import org.example.project.qdcore.ast.base.inline.StrongEmphasis
import org.example.project.qdcore.ast.base.inline.SubdocumentLink
import org.example.project.qdcore.ast.base.inline.Text
import org.example.project.qdcore.ast.quarkdown.inline.MathSpan
import org.example.project.qdcore.ast.quarkdown.inline.TextSymbol
import org.example.project.qdcore.context.MutableContext
import org.example.project.qdcore.context.options.isSubdocumentUrl
import org.example.project.qdcore.document.size.Size
import org.example.project.qdcore.flavor.InlineLexerVariant
import org.example.project.qdcore.function.value.factory.IllegalRawValueException
import org.example.project.qdcore.function.value.factory.ValueFactory
import org.example.project.qdcore.lexer.Lexer
import org.example.project.qdcore.lexer.Token
import org.example.project.qdcore.lexer.TokenData
import org.example.project.qdcore.lexer.acceptAll
import org.example.project.qdcore.lexer.tokens.CodeSpanToken
import org.example.project.qdcore.lexer.tokens.CommentToken
import org.example.project.qdcore.lexer.tokens.CriticalContentToken
import org.example.project.qdcore.lexer.tokens.DiamondAutolinkToken
import org.example.project.qdcore.lexer.tokens.EmphasisToken
import org.example.project.qdcore.lexer.tokens.EntityToken
import org.example.project.qdcore.lexer.tokens.EscapeToken
import org.example.project.qdcore.lexer.tokens.ImageToken
import org.example.project.qdcore.lexer.tokens.InlineMathToken
import org.example.project.qdcore.lexer.tokens.LineBreakToken
import org.example.project.qdcore.lexer.tokens.LinkToken
import org.example.project.qdcore.lexer.tokens.PlainTextToken
import org.example.project.qdcore.lexer.tokens.ReferenceFootnoteToken
import org.example.project.qdcore.lexer.tokens.ReferenceImageToken
import org.example.project.qdcore.lexer.tokens.ReferenceLinkToken
import org.example.project.qdcore.lexer.tokens.StrikethroughToken
import org.example.project.qdcore.lexer.tokens.StrongEmphasisToken
import org.example.project.qdcore.lexer.tokens.StrongToken
import org.example.project.qdcore.lexer.tokens.TextSymbolToken
import org.example.project.qdcore.lexer.tokens.UrlAutolinkToken
import org.example.project.qdcore.misc.color.Color
import org.example.project.qdcore.misc.color.decoder.HexColorDecoder
import org.example.project.qdcore.misc.color.decoder.HsvHslColorDecoder
import org.example.project.qdcore.misc.color.decoder.RgbColorDecoder
import org.example.project.qdcore.misc.color.decoder.RgbaColorDecoder
import org.example.project.qdcore.misc.color.decoder.decode
import org.example.project.qdcore.util.Escape
import org.example.project.qdcore.util.iterator
import org.example.project.qdcore.util.nextOrNull
import org.example.project.qdcore.util.trimDelimiters
import org.example.project.qdcore.visitor.token.InlineTokenVisitor

/**
 * ASCII of the character that replaces null characters,
 * following CommonMark's security guideline _(2.3 Insecure characters)_.
 */
private const val NULL_CHAR_REPLACEMENT_ASCII = 65533

/**
 * A parser for inline tokens.
 * @param context additional data to fill during the parsing process
 */
class InlineTokenParser(
    private val context: MutableContext,
) : InlineTokenVisitor<Node> {
    /**
     * @return the parsed content of the tokenization from [this] lexer
     */
    private fun Lexer.tokenizeAndParse(): List<Node> =
        this
            .tokenize()
            .acceptAll(context.flavor.parserFactory.newParser(context))

    /**
     * Tokenizes and parses sub-nodes.
     * @param source source to tokenize using the default inline lexer from this flavor
     * @return parsed nodes
     */
    private fun parseSubContent(source: CharSequence) =
        context.flavor.lexerFactory
            .newInlineLexer(source)
            .tokenizeAndParse()

    /**
     * Tokenizes and parses sub-nodes within a link label.
     * @param source source to tokenize using the link label inline lexer from this flavor
     * @return parsed nodes
     */
    private fun parseLinkLabelSubContent(source: CharSequence) =
        context.flavor.lexerFactory
            .newInlineLexer(source, variant = InlineLexerVariant.LINK_LABEL)
            .tokenizeAndParse()

    override fun visit(token: EscapeToken): Node {
        val groups = token.data.groups.iterator(consumeAmount = 2)
        return Text(text = groups.next())
    }

    override fun visit(token: EntityToken): Node {
        val groups = token.data.groups.iterator(consumeAmount = 2)
        val entity = groups.next().trim().lowercase()

        /**
         * @param radix radix to decode the numeric value for (`radix = 10` for decimal, `radix = 16` for hexadecimal)
         * @return [this] string to its corresponding character in [radix] representation.
         */
        fun String.decodeToContent(radix: Int): String {
            val ascii = toIntOrNull(radix) ?: return ""
            // CommonMark's security guideline (2.3 Insecure characters)
            return if (ascii != 0) {
                ascii.toChar()
            } else {
                NULL_CHAR_REPLACEMENT_ASCII.toChar()
            }.toString()
        }

        // Critical because further checks and mappings may be required during the rendering stage.
        return CriticalContent(
            when {
                entity == "colon" -> ":"

                // Hexadecimal (e.g. &#xD06)
                entity.startsWith("#x") -> groups.next().decodeToContent(radix = 16)

                // Decimal (e.g. &#35)
                entity.startsWith("#") -> groups.next().decodeToContent(radix = 10)

                // HTML entity (e.g. &nbsp;)
                else -> Escape.Html.unescape(token.data.text)
            },
        )
    }

    override fun visit(token: CriticalContentToken): Node = CriticalContent(token.data.text)

    override fun visit(token: TextSymbolToken): Node {
        // The symbol is then treated separately from text in the renderer.
        // e.g. the HTML renderer converts the symbol to its corresponding HTML entity (© -> &copy;).
        return TextSymbol(token.symbol.result)
    }

    override fun visit(token: CommentToken): Node {
        // Content is ignored.
        return Comment
    }

    override fun visit(token: LineBreakToken): Node = LineBreak

    override fun visit(token: LinkToken): LinkNode {
        val groups = token.data.groups.iterator(consumeAmount = 2)
        val link =
            Link(
                label = parseLinkLabelSubContent(groups.next()),
                url = groups.next().trim(),
                // Removes leading and trailing delimiters.
                title =
                    groups
                        .nextOrNull()
                        ?.trimDelimiters()
                        ?.trim()
                        ?.let(::parseSubContent),
                fileSystem = context.fileSystem,
            )

        // The anchor is stripped from the URL, if present, to allow proper subdocument detection.
        // If the stripped URL points to a subdocument, it is a subdocument link.
        val result = link.stripAnchor()
        val strippedLink = result?.first ?: link
        val anchor = result?.second

        return when {
            context.isSubdocumentUrl(strippedLink.url) -> SubdocumentLink(strippedLink, anchor)
            else -> link
        }
    }

    override fun visit(token: ReferenceLinkToken): ReferenceLink {
        val groups = token.data.groups.iterator(consumeAmount = 2)
        val label = parseLinkLabelSubContent(groups.next())
        // When the reference is collapsed, the label is the same as the reference label.
        return ReferenceLink(
            label = label,
            referenceLabel = groups.nextOrNull()?.let { parseLinkLabelSubContent(it) } ?: label,
            fallback = { Text(token.data.text) },
        )
    }

    override fun visit(token: ReferenceFootnoteToken): Node {
        val groups = token.data.groups.iterator(consumeAmount = 2)
        val label = groups.next()
        val definition = groups.nextOrNull()

        return when {
            // All-in-one case:
            // Named: [^label: definition]
            // Anonymous: [^: definition]
            definition != null -> {
                ReferenceDefinitionFootnote(
                    label.takeUnless { it.isBlank() } ?: context.newUuid(),
                    definition = parseSubContent(definition),
                )
            }

            // Reference only case.
            else -> {
                ReferenceFootnote(
                    label,
                    fallback = { Text(token.data.text) },
                )
            }
        }
    }

    override fun visit(token: DiamondAutolinkToken): Node {
        val groups = token.data.groups.iterator(consumeAmount = 2)
        val url = groups.next().trim()
        return visit(UrlAutolinkToken(token.data.copy(text = url)))
    }

    override fun visit(token: UrlAutolinkToken): Node {
        val url = token.data.text.trim()
        return Link(
            label = listOf(Text(url)),
            url = url,
            title = null,
        )
    }

    /**
     * Given an image token, extracts its width and height, if they are set.
     * They are stored in the named groups `width` and `height`, both prefixed by [namedGroupPrefix].
     * @param namedGroupPrefix prefix of the named groups
     * @param data token data to extract the size from
     * @return pair of width and height, or `null` if they are either unset or invalid
     */
    private fun extractImageSize(
        namedGroupPrefix: String,
        data: TokenData,
    ): Pair<Size?, Size?> {
        val width = data.namedGroups["${namedGroupPrefix}width"]
        val height = data.namedGroups["${namedGroupPrefix}height"]

        fun toSize(raw: String?): Size? =
            try {
                raw?.let(ValueFactory::size)?.unwrappedValue // Parses the value.
            } catch (_: IllegalRawValueException) {
                null
            }

        return toSize(width) to toSize(height)
    }

    override fun visit(token: ImageToken): Node {
        val link = visit(LinkToken(token.data))
        val (width, height) = extractImageSize("img", token.data)
        val referenceId = token.data.namedGroups["imgcustomid"]?.trim()

        return Image(link, width, height, referenceId)
    }

    override fun visit(token: ReferenceImageToken): Node {
        val link = visit(ReferenceLinkToken(token.data))
        val (width, height) = extractImageSize("refimg", token.data)
        val referenceId = token.data.namedGroups["refimgcustomid"]?.trim()

        return ReferenceImage(link, width, height, referenceId)
    }

    override fun visit(token: CodeSpanToken): Node {
        val groups = token.data.groups.iterator(consumeAmount = 3)
        val rawText = groups.next().replace("\n", " ")

        // If the text start and ends by a space, and does contain non-space characters,
        // the leading and trailing spaces are trimmed (according to CommonMark).
        val hasNonSpaceChars = rawText.any { it != ' ' }
        val hasSpaceCharsOnBothEnds = rawText.firstOrNull() == ' ' && rawText.lastOrNull() == ' '

        // Trimmed final text.
        val text =
            if (hasNonSpaceChars && hasSpaceCharsOnBothEnds) {
                rawText.trimDelimiters()
            } else {
                rawText
            }

        // Additional content brought by the code span.
        // If null, no additional content is present.
        val content: CodeSpan.ContentInfo? =
            // Color decoding. Named colors are disabled due to performance reasons.
            Color
                .decode(text, HexColorDecoder, RgbColorDecoder, RgbaColorDecoder, HsvHslColorDecoder)
                ?.let(CodeSpan::ColorContent)

        return CodeSpan(text, content)
    }

    override fun visit(token: PlainTextToken): Node = Text(token.data.text)

    /**
     * @param token emphasis token to parse the content for
     * @return parsed content of an emphasis token
     */
    private fun emphasisContent(token: Token): InlineContent {
        // The raw string content, without the delimiters.
        val text =
            token.data.groups
                .iterator(consumeAmount = 3)
                .next()
        return parseSubContent(text)
    }

    override fun visit(token: EmphasisToken): Node = Emphasis(emphasisContent(token))

    override fun visit(token: StrongToken): Node = Strong(emphasisContent(token))

    override fun visit(token: StrongEmphasisToken): Node = StrongEmphasis(emphasisContent(token))

    override fun visit(token: StrikethroughToken): Node = Strikethrough(emphasisContent(token))

    override fun visit(token: InlineMathToken): Node {
        val groups = token.data.groups.iterator(consumeAmount = 2)
        return MathSpan(expression = groups.next().trim())
    }
}
