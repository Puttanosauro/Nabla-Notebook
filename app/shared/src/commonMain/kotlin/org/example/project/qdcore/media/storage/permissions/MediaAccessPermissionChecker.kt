package org.example.project.qdcore.media.storage.permissions

import org.example.project.qdcore.media.LocalMedia
import org.example.project.qdcore.media.MediaVisitor
import org.example.project.qdcore.media.RemoteMedia
import org.example.project.qdcore.permissions.Permission
import org.example.project.qdcore.permissions.PermissionHolder
import org.example.project.qdcore.permissions.requirePermission
import org.example.project.qdcore.permissions.requireReadPermission

/**
 * Checks whether a media type is allowed to be stored according to the granted permissions in [context].
 * If a required permission is missing, a [org.example.project.qdcore.permissions.MissingPermissionException] will be thrown.
 * @param context the context to access the media storage from
 */
class MediaAccessPermissionChecker(
    private val holder: PermissionHolder,
) : MediaVisitor<Unit> {
    override fun visit(media: LocalMedia) {
        holder.requireReadPermission(media.file)
    }

    override fun visit(media: RemoteMedia) {
        holder.requirePermission(
            Permission.NetworkAccess,
            message = "Cannot access remote media ${media.url}",
        )
    }
}
