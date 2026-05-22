package org.example.project.qdcore.context.hooks

import org.example.project.qdcore.RUNTIME_ERROR_EXIT_CODE
import org.example.project.qdcore.ast.attributes.error.setError
import org.example.project.qdcore.ast.base.inline.SubdocumentLink
import org.example.project.qdcore.ast.base.inline.setSubdocument
import org.example.project.qdcore.ast.iterator.AstIteratorHook
import org.example.project.qdcore.ast.iterator.ObservableAstIterator
import org.example.project.qdcore.context.MutableContext
import org.example.project.qdcore.context.subdocument.findResourceByPath
import org.example.project.qdcore.context.subdocument.subdocumentGraph
import org.example.project.qdcore.document.sub.Subdocument
import org.example.project.qdcore.permissions.MissingPermissionException
import org.example.project.qdcore.permissions.requireReadPermission
import org.example.project.qdcore.pipeline.error.PipelineException

/**
 * Hook that registers [Subdocument]s in the subdocument graph of [context].
 * A subdocument is a separate document file that is referenced by a link from the main document or another subdocument.
 * @param context the context to attach this hook to
 */
class SubdocumentRegistrationHook(
    private val context: MutableContext,
) : AstIteratorHook {
    override fun attach(iterator: ObservableAstIterator) {
        iterator.on<SubdocumentLink> { link ->
            val fileSystem = link.fileSystem ?: context.fileSystem
            val file = fileSystem.resolve(path = link.url)

            if (!file.exists()) {
                link.setError(UnresolvedSubdocumentException(link), context)
                return@on
            }

            try {
                context.requireReadPermission(file)
            } catch (e: MissingPermissionException) {
                link.setError(e, context)
                return@on
            }

            val path = file.canonicalFile.absolutePath

            // Reuse an already-registered subdocument to avoid redundant file I/O.
            val subdocument =
                context.subdocumentGraph.findResourceByPath(path)
                    ?: Subdocument.Resource(
                        name = file.nameWithoutExtension,
                        path = path,
                        workingDirectory = file.parentFile.canonicalFile,
                        content = file.readText(),
                    )

            link.setSubdocument(context, subdocument)

            context.subdocumentGraph =
                context.subdocumentGraph
                    .addVertexAndEdge(
                        vertex = subdocument,
                        edgeFrom = context.subdocument,
                        edgeTo = subdocument,
                    )
        }
    }
}

/**
 * Exception thrown when a [SubdocumentLink] cannot be resolved to an existing resource.
 * @param link the link that failed to resolve
 */
class UnresolvedSubdocumentException(
    link: SubdocumentLink,
) : PipelineException(
        message = "Cannot resolve subdocument link: ${link.url}",
        code = RUNTIME_ERROR_EXIT_CODE,
    )
