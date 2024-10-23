package cz.moro.usermanagment.controller.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Response representing a User")
data class UserResponse(
    @Schema(description = "ID of user", example = "1")
    val id: Long,
    @Schema(description = "Name of the user", example = "Alice")
    val name: String,
    @Schema(description = "Username", example = "alice02")
    val userName: String
)
