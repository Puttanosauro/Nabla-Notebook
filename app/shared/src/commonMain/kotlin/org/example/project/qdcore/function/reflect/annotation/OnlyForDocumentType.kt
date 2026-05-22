package org.example.project.qdcore.function.reflect.annotation

import org.example.project.qdcore.document.DocumentType
import org.example.project.qdcore.function.call.validate.DocumentTypeFunctionCallValidator
import org.example.project.qdcore.function.value.OutputValue

/**
 * When a library function is annotated with this annotation, it can only be called if the document adopts one of the given document types.
 * @param types allowed document types
 */
@Target(AnnotationTarget.FUNCTION)
annotation class OnlyForDocumentType(
    vararg val types: DocumentType,
)

/**
 * When a library function is annotated with this annotation, it can only be called if the document adopts none of the given document types.
 * This is the opposite of [OnlyForDocumentType].
 * @param types allowed document types
 */
@Target(AnnotationTarget.FUNCTION)
annotation class NotForDocumentType(
    vararg val types: DocumentType,
)

/**
 * Converts an [OnlyForDocumentType] annotation to a [DocumentTypeFunctionCallValidator]
 */
fun <T : OutputValue<*>> OnlyForDocumentType.toValidator() = DocumentTypeFunctionCallValidator<T>(types.toSet())

/**
 * Converts an [NotForDocumentType] annotation to a [DocumentTypeFunctionCallValidator]
 */
fun <T : OutputValue<*>> NotForDocumentType.toValidator() = DocumentTypeFunctionCallValidator<T>(DocumentType.entries - types.toSet())
