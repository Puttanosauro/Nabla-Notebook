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
 * A converter of a [Media] to an [OutputResource].
 * @param name generated media name
 */
class MediaOutputResourceConverter(
    private val name: String,
) : MediaVisitor<OutputResource> {
    override fun visit(media: LocalMedia) =
        FileReferenceOutputArtifact(
            name,
            media.file,
            useChecksumInvalidation = true,
        )

    override fun visit(media: RemoteMedia) =
        BinaryOutputArtifact(
            name,
            media.url
                .openStream()
                .readBytes()
                .toList(),
            ArtifactType.AUTO,
        )
}
