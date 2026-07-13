package org.example.project.qdcore.media.export

import org.example.project.qdcore.media.LocalMedia
import org.example.project.qdcore.media.Media
import org.example.project.qdcore.media.MediaVisitor
import org.example.project.qdcore.media.RemoteMedia
import org.example.project.qdcore.pipeline.output.ArtifactType
import org.example.project.qdcore.pipeline.output.BinaryOutputArtifact
import org.example.project.qdcore.pipeline.output.FileReferenceOutputArtifact
import org.example.project.qdcore.pipeline.output.OutputResource

/**
 * KMP Expectation: Each platform defines how to synchronously fetch bytes from a URL.
 * (JVM uses java.net.URL or Ktor, Web uses fetch or returns a placeholder).
 */
expect fun downloadRemoteMediaBytes(url: String): ByteArray

/**
 * A converter of a [Media] to an [OutputResource].
 * @param name generated media name
 */
class MediaOutputResourceConverter(
    private val name: String,
) : MediaVisitor<OutputResource> {

    override fun visit(media: LocalMedia) =
        FileReferenceOutputArtifact(
            name = name,
            sourcePath = media.filePath,
            useChecksumInvalidation = false,
        )

    override fun visit(media: RemoteMedia) =
        BinaryOutputArtifact.fromFile(
            name = name,
            bytes = downloadRemoteMediaBytes(media.url)
        )
}