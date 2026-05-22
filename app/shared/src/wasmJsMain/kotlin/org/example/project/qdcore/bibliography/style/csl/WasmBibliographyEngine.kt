package org.example.project.qdcore.bibliography.style.csl

import org.example.project.qdcore.ast.InlineContent
import org.example.project.qdcore.ast.base.inline.Text
import org.example.project.qdcore.bibliography.BibliographyEntry

class WasmBibliographyEngine : BibliographyEngine {
    override fun getCitationLabel(entries: List<BibliographyEntry>) = "[?]"
    override fun getListLabel(entry: BibliographyEntry, index: Int) = "[${index + 1}]"
    override fun contentOf(entry: BibliographyEntry) = listOf(Text("Bibliography disabled on web."))
}