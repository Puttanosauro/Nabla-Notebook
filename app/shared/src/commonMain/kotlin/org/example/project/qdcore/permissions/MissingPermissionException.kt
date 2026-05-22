package org.example.project.qdcore.permissions

import org.example.project.qdcore.MISSING_PERMISSION_EXIT_CODE
import org.example.project.qdcore.ast.dsl.buildInline
import org.example.project.qdcore.pipeline.error.PipelineException

/**
 * Thrown when an operation requires a [Permission] that has not been granted by its [PermissionHolder].
 * @param message a descriptive message explaining the context of the failed permission check
 * @param missingPermission the [Permission] that was required but not granted
 */
class MissingPermissionException(
    message: String,
    missingPermission: Permission,
) : PipelineException(
        richMessage =
            buildInline {
                text("$message: not enough privileges to perform this action. ")
                lineBreak()
                text("Missing required permission: ")
                strong { codeSpan(missingPermission.name) }
                text(". ")
                lineBreak()
                text("To grant this permission, run with: ")
                codeSpan("--allow ${missingPermission.name}")
            },
        code = MISSING_PERMISSION_EXIT_CODE,
    )
