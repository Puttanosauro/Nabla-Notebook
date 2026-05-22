package org.example.project.qdcore.context

import org.example.project.qdcore.ast.attributes.AstAttributes
import org.example.project.qdcore.ast.quarkdown.FunctionCallNode
import org.example.project.qdcore.context.file.FileSystem
import org.example.project.qdcore.context.file.RootGranularity
import org.example.project.qdcore.context.file.SimpleFileSystem
import org.example.project.qdcore.context.file.getRootFileSystem
import org.example.project.qdcore.context.options.ContextOptions
import org.example.project.qdcore.context.options.MutableContextOptions
import org.example.project.qdcore.context.subdocument.SubdocumentsData
import org.example.project.qdcore.document.DocumentInfo
import org.example.project.qdcore.document.sub.Subdocument
import org.example.project.qdcore.flavor.MarkdownFlavor
import org.example.project.qdcore.function.Function
import org.example.project.qdcore.function.call.FunctionCall
import org.example.project.qdcore.function.call.UncheckedFunctionCall
import org.example.project.qdcore.function.library.Library
import org.example.project.qdcore.graph.DirectedGraph
import org.example.project.qdcore.graph.VisitableOnceGraph
import org.example.project.qdcore.graph.visitableOnce
import org.example.project.qdcore.localization.Locale
import org.example.project.qdcore.localization.LocalizationKeyNotFoundException
import org.example.project.qdcore.localization.LocalizationLocaleNotFoundException
import org.example.project.qdcore.localization.LocalizationTable
import org.example.project.qdcore.localization.LocalizationTableNotFoundException
import org.example.project.qdcore.media.storage.MutableMediaStorage
import org.example.project.qdcore.media.storage.ReadOnlyMediaStorage
import org.example.project.qdcore.permissions.Permission
import org.example.project.qdcore.pipeline.Pipeline
import org.example.project.qdcore.pipeline.Pipelines

/**
 * An immutable [Context] implementation.
 * This might be used in tests as a toy context, but in a concrete execution, its mutable subclass [MutableContext] is used.
 * @param attributes attributes of the node tree, produced by the parsing stage
 * @param flavor Markdown flavor used for this pipeline. It specifies how to produce the needed components
 * @param libraries loaded libraries to look up functions from
 * @param subdocument the subdocument this context is processing
 */
open class BaseContext(
    override val attributes: AstAttributes,
    override val flavor: MarkdownFlavor,
    override val libraries: Set<Library> = emptySet(),
    override val subdocument: Subdocument = Subdocument.Root,
) : Context {
    override val attachedPipeline: Pipeline?
        get() = Pipelines.getAttachedPipeline(this)

    override val documentInfo = DocumentInfo()

    override val options: ContextOptions = MutableContextOptions()

    override val loadableLibraries = emptySet<Library>()

    override val localizationTables = emptyMap<String, LocalizationTable>()

    override val mediaStorage: ReadOnlyMediaStorage by lazy {
        MutableMediaStorage(options, permissionHolder = this)
    }

    override val sharedSubdocumentsData: SubdocumentsData<VisitableOnceGraph<Subdocument>> =
        SubdocumentsData(
            graph = DirectedGraph<Subdocument>().visitableOnce,
            withContexts = mapOf(subdocument to this),
        )

    override val fileSystem: FileSystem by lazy {
        val workingDirectory =
            (subdocument as? Subdocument.Resource)?.workingDirectory
                ?: attachedPipeline?.options?.workingDirectory
        SimpleFileSystem(workingDirectory)
    }

    override val permissions: Set<Permission> by lazy {
        attachedPipeline?.options?.permissions.orEmpty()
    }

    override val rootFileSystem: FileSystem? by lazy {
        this.getRootFileSystem(granularity = RootGranularity.PROJECT)
    }

    override fun getFunctionByName(name: String): Function<*>? =
        libraries
            .asSequence()
            .flatMap { it.functions }
            .find { it.name == name }

    override fun resolve(call: FunctionCallNode): FunctionCall<*>? {
        val function = getFunctionByName(call.name)

        return function?.let {
            FunctionCall(
                it,
                call.arguments,
                context = this,
                sourceNode = call,
            )
        }
    }

    override fun resolveUnchecked(call: FunctionCallNode): UncheckedFunctionCall<*> = UncheckedFunctionCall(call.name) { resolve(call) }

    override fun localize(
        tableName: String,
        key: String,
        locale: Locale,
    ): String {
        val table = localizationTables[tableName] ?: throw LocalizationTableNotFoundException(tableName)
        val entries = table[locale] ?: throw LocalizationLocaleNotFoundException(tableName, locale)
        return entries[key.lowercase()] ?: entries[key]
            ?: throw LocalizationKeyNotFoundException(tableName, locale, key)
    }

    override fun fork(): ScopeContext = throw UnsupportedOperationException("Forking is not supported in BaseContext")
}
