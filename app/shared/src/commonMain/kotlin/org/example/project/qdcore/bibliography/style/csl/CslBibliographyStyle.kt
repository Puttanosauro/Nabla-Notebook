package org.example.project.qdcore.bibliography.style.csl

import org.example.project.qdcore.ast.InlineContent
import org.example.project.qdcore.bibliography.BibliographyEntry
import org.example.project.qdcore.bibliography.style.BibliographyEntryLabelProviderStrategy
import org.example.project.qdcore.bibliography.style.BibliographyStyle
// In commonMain
interface BibliographyEngine {
    fun getCitationLabel(entries: List<BibliographyEntry>): String
    fun getListLabel(entry: BibliographyEntry, index: Int): String
    fun contentOf(entry: BibliographyEntry): InlineContent
}

// In commonMain
class CslBibliographyStyle(
    private val cslStyleName: String,
    private val engine: BibliographyEngine // Use the interface!
) : BibliographyStyle {
    override val name = cslStyleName

    // Now the class delegates to the engine, not the Java library
    override val labelProvider = object : BibliographyEntryLabelProviderStrategy {
        override fun getCitationLabel(entries: List<BibliographyEntry>) = engine.getCitationLabel(entries)
        override fun getListLabel(entry: BibliographyEntry, index: Int) = engine.getListLabel(entry, index)
    }

    override fun contentOf(entry: BibliographyEntry) = engine.contentOf(entry)
}