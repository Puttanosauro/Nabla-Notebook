package org.example.project.qdcore.bibliography.style.csl

import org.example.project.qdcore.ast.InlineContent
import org.example.project.qdcore.bibliography.BibliographyEntry
import org.example.project.qdcore.bibliography.style.BibliographyEntryLabelProviderStrategy
import org.example.project.qdcore.bibliography.style.BibliographyStyle

// 1. The Interface
interface BibliographyEngine {
    fun getCitationLabel(entries: List<BibliographyEntry>): String
    fun getListLabel(entry: BibliographyEntry, index: Int): String
    fun contentOf(entry: BibliographyEntry): InlineContent
}

// 2. The Expect Factory (This is new!)
expect fun createBibliographyEngine(
    cslStyleName: String,
    content: String,
    filename: String,
    localeTag: String?
): BibliographyEngine

// 3. The Class
class CslBibliographyStyle(
    private val cslStyleName: String,
    private val engine: BibliographyEngine
) : BibliographyStyle {
    override val name = cslStyleName

    override val labelProvider = object : BibliographyEntryLabelProviderStrategy {
        override fun getCitationLabel(entries: List<BibliographyEntry>) = engine.getCitationLabel(entries)
        override fun getListLabel(entry: BibliographyEntry, index: Int) = engine.getListLabel(entry, index)
    }

    override fun contentOf(entry: BibliographyEntry) = engine.contentOf(entry)

    // 4. The Companion Object (This is new!)
    companion object {
        fun from(
            cslStyleName: String,
            content: String,
            filename: String,
            localeTag: String? = null,
        ): CslBibliographyStyle {
            // Uses the factory to get either the WASM or JVM engine!
            val engine = createBibliographyEngine(cslStyleName, content, filename, localeTag)
            return CslBibliographyStyle(cslStyleName, engine)
        }
    }
}