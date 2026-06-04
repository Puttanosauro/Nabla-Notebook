package org.example.project.qdcore.pipeline.output

import org.example.project.qdcore.pipeline.error.IOPipelineException

/**
 * Tells KMP: "Each platform (JVM, WASM) will provide its own specific way
 * to read internal bundled resources (like default fonts or CSS)."
 */
expect fun readInternalResourceBytes(resource: String): List<Byte>?

/**
 * Represents a [BinaryOutputArtifact] whose content is lazily loaded on demand (via [accept]).
 * @param name name of the resource (without file extensions)
 * @param content supplier of the content of the resource, retrieved upon visit
 * @param type type of content the resource contains
 */
data class LazyOutputArtifact(
    override val name: String,
    override val content: () -> List<Byte>,
    override val type: ArtifactType,
) : OutputArtifact<() -> List<Byte>> {

    // When visited, the content is loaded and a [BinaryOutputArtifact] is created and visited instead.
    override fun <T> accept(visitor: OutputResourceVisitor<T>): T =
        visitor.visit(BinaryOutputArtifact(name, content(), type))

    companion object {

        /**
         * Creates a [LazyOutputArtifact] whose content is extracted from an internal resource.
         * @param resource path to the internal resource
         * @param name name of the resource (without file extensions)
         * @param type type of content the resource contains
         */
        fun internal(
            resource: String,
            name: String,
            type: ArtifactType,
        ) = LazyOutputArtifact(
            name = name,
            content = {
                readInternalResourceBytes(resource)
                    ?: throw IOPipelineException("Resource $resource not found")
            },
            type = type,
        )

        /**
         * Like [internal], but reads the resource instantly and returns `null` if it does not exist.
         */
        fun internalOrNull(
            resource: String,
            name: String,
            type: ArtifactType,
        ): LazyOutputArtifact? =
            readInternalResourceBytes(resource)?.let {
                LazyOutputArtifact(name, content = { it }, type)
            }
    }
}