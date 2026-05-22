package org.example.project.qdcore.bibliography.style.csl

// Put your heavy Java imports ONLY here
import de.undercouch.citeproc.CSL
import org.example.project.qdcore.ast.InlineContent
import org.example.project.qdcore.bibliography.BibliographyEntry

class JvmBibliographyEngine(
    private val csl: CSL,
    private val format: QuarkdownCslFormat
) : BibliographyEngine {

    override fun getCitationLabel(entries: List<BibliographyEntry>): String {
        csl.makeCitation(*entries.map { it.citationKey }.toTypedArray())
        return format.lastCitationResult.toString() // Or your specific toPlainText logic
    }

    override fun getListLabel(entry: BibliographyEntry, index: Int): String {
        // Implement your original logic here using the 'csl' variable
        return "..."
    }

    override fun contentOf(entry: BibliographyEntry): InlineContent {
        // Implement your original logic here
        return emptyList()
    }
}