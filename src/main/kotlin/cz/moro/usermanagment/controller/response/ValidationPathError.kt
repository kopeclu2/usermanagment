package cz.moro.usermanagment.controller.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Represents an error encountered during validation")
    data class ValidationPathError(
        @Schema(
            description = "The path to the field that caused the validation error",
            example = "user.email",
        )
        val path: String,
        @Schema(
            description = "A message describing the validation error",
            example = "Email address must be valid",
        )
        val message: String,
        @Schema(
            description = "The value that was provided for the field",
            example = "invalid-email",
        )
        val value: Any?,
        @Schema(
            description = "The type of validation error",
            example = "FORMAT",
        )
        val objectName: String?,
    )