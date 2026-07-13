# Vendored: better-parse

Source: https://github.com/h0tk3y/better-parse (Apache License 2.0, see LICENSE in this directory)

better-parse publishes only `jvm`, `js`, and native artifacts — there is no `wasmJs`
artifact on Maven Central. Since the library's `commonMain` is pure Kotlin, its sources
are vendored here so they compile for every target of this module (js, wasmJs, and a
future jvm/desktop target).

## Local modifications (both in `lexer/`)

1. **`RegexToken.kt`** — was `expect class` with three platform `actual`s whose only job
   was anchoring the regex match at `fromIndex` (JS: sticky-flag hack via `asDynamic()`,
   which does not exist on Kotlin/Wasm; Native: `\A` pattern prefix + CharSequence wrapper;
   JVM: region-bounded `java.util.regex.Matcher`). Replaced by ONE common-Kotlin class
   using `Regex.matchAt()`, which provides anchored matching on every target.
   Public API (constructors, `regexToken`/`token` factory functions) is unchanged.

2. **`Token.kt`** — `Language` was an `@OptionalExpectation expect annotation class`
   (JVM actual: typealias to IntelliJ's `org.intellij.lang.annotations.Language`, purely
   for IDE regex highlighting). Now a plain annotation class, so the vendored sources
   contain no expect/actual declarations at all.

Everything else is verbatim upstream `commonMain` (including the pre-generated
`andFunctions.kt` and `tuples.kt`, moved from the `generated/` source root into their
package directories).
