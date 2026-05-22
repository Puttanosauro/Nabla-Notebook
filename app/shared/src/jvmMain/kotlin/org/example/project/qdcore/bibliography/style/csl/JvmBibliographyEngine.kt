package org.example.project.qdcore.bibliography.style.csl

import de.undercouch.citeproc.BibliographyFileReader
import de.undercouch.citeproc.CSL
import org.example.project.qdcore.ast.InlineContent
import org.example.project.qdcore.bibliography.BibliographyEntry
import org.example.project.qdcore.util.node.toPlainText

// This matches the 'expect' in your commonMain file perfectly
actual fun createBibliographyEngine(
    cslStyleName: String,
    content: String,
    filename: String,
    localeTag: String?
): BibliographyEngine {
    // We convert the string content back to an InputStream for Java
    val stream = content.encodeToByteArray().inputStream()
    val provider = BibliographyFileReader().readBibliographyFile(stream, filename)
    return JvmBibliographyEngine(cslStyleName, provider, localeTag)
}

// Your original heavy JVM Class logic
class JvmBibliographyEngine(
    cslStyleName: String,
    private val provider: de.undercouch.citeproc.ItemDataProvider,
    localeTag: String?
) : BibliographyEngine {

    private val format = QuarkdownCslFormat()
    private val csl = CSL(provider, cslStyleName, localeTag).apply {
        setOutputFormat(format)
        registerCitationItems(provider.ids)
    }

    private val formattedEntries: Map<String, FormattedBibliographyEntry> by lazy {
        format.bibliographyEntries.clear()
        csl.makeBibliography()
        provider.ids.zip(format.bibliographyEntries).toMap()
    }

    override fun getCitationLabel(entries: List<BibliographyEntry>): String {
        csl.makeCitation(*entries.map { it.citationKey }.toTypedArray())
        return format.lastCitationResult.toPlainText().ifBlank { "[?]" }
    }

    override fun getListLabel(entry: BibliographyEntry, index: Int): String {
        return formattedEntries[entry.citationKey]?.label ?: ""
    }

    override fun contentOf(entry: BibliographyEntry): InlineContent {
        return formattedEntries[entry.citationKey]?.content ?: emptyList()
    }
}