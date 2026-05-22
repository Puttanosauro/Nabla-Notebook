package org.example.project.qdcore.ast.attributes.localization

/**
 * Keys for localization of kinds of nodes,
 * used to look up localized strings in the default [org.example.project.qdcore.localization.LocalizationTable].
 * @see LocalizedKind
 */
object LocalizedKindKeys {
    /**
     * @see org.example.project.qdcore.ast.base.block.Code
     */
    const val CODE_BLOCK = "listing"

    /**
     * @see org.example.project.qdcore.ast.quarkdown.block.Figure
     */
    const val FIGURE = "figure"

    /**
     * @see org.example.project.qdcore.ast.base.block.Heading
     */
    const val HEADING = "section"

    /**
     * @see org.example.project.qdcore.ast.base.block.Table
     */
    const val TABLE = "table"
}
