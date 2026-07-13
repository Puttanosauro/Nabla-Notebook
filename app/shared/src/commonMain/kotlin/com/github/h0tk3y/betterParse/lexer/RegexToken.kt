package com.github.h0tk3y.betterParse.lexer

/*
 * Vendored from better-parse (https://github.com/h0tk3y/better-parse), Apache License 2.0.
 *
 * Modified for Nabla-Notebook: the original `expect class RegexToken` with JVM/JS/Native
 * `actual` implementations has been replaced by this single pure common-Kotlin class.
 * The platform-specific implementations only existed to anchor the regex match at
 * `fromIndex` (JS sticky flag hack via asDynamic(), `\A` prefix on Native, region-bounded
 * Matcher on JVM). `Regex.matchAt()` (Kotlin stdlib >= 1.7) provides exactly that anchored
 * behavior on every target, including wasmJs.
 */
public class RegexToken : Token {
    private val pattern: String
    private val regex: Regex

    public constructor(name: String?, @Language("RegExp", "", "") patternString: String, ignored: Boolean = false) :
        super(name, ignored) {
        pattern = patternString
        regex = patternString.toRegex()
    }

    public constructor(name: String?, regex: Regex, ignored: Boolean = false) : super(name, ignored) {
        pattern = regex.pattern
        this.regex = regex
    }

    override fun match(input: CharSequence, fromIndex: Int): Int =
        regex.matchAt(input, fromIndex)?.value?.length ?: 0

    override fun toString(): String = "${name ?: ""} [$pattern]" + if (ignored) " [ignorable]" else ""
}

public fun regexToken(name: String, @Language("RegExp", "", "") pattern: String, ignore: Boolean = false): RegexToken =
    RegexToken(name, pattern, ignore)

public fun regexToken(name: String, pattern: Regex, ignore: Boolean = false): RegexToken =
    RegexToken(name, pattern, ignore)

public fun regexToken(@Language("RegExp", "", "") pattern: String, ignore: Boolean = false): RegexToken =
    RegexToken(null, pattern, ignore)

public fun regexToken(pattern: Regex, ignore: Boolean = false): RegexToken = RegexToken(null, pattern, ignore)

@Deprecated(
    "Use either regexToken or literalToken. This function will be removed soon",
    ReplaceWith("regexToken(pattern, ignore)")
)
public fun token(name: String, @Language("RegExp", "", "") pattern: String, ignore: Boolean = false): RegexToken =
    RegexToken(name, pattern, ignore)

@Deprecated(
    "Use either regexToken or literalToken. This function will be removed soon",
    ReplaceWith("regexToken(pattern, ignore)")
)
public fun token(name: String, pattern: Regex, ignore: Boolean = false): RegexToken = RegexToken(name, pattern, ignore)

@Deprecated(
    "Use either regexToken or literalToken. This function will be removed soon",
    ReplaceWith("regexToken(pattern, ignore)")
)
public fun token(@Language("RegExp", "", "") pattern: String, ignore: Boolean = false): RegexToken =
    RegexToken(null, pattern, ignore)

@Deprecated(
    "Use either regexToken or literalToken. This function will be removed soon",
    ReplaceWith("regexToken(pattern, ignore)")
)
public fun token(pattern: Regex, ignore: Boolean = false): RegexToken = RegexToken(null, pattern, ignore)
