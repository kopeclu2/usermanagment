package cz.moro.usermanagment.controller.request

import cz.moro.usermanagment.annotations.ValidPassword
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "Request object representing a User")
data class UserRequest(
    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
    @Schema(description = "Name of the user", example = "Alice")
    val name: String,
    @field:NotBlank(message = "Username cannot be blank")
    @field:Size(min = 2, max = 255, message = "Username must be between 2 and 255 characters")
    @Schema(description = "Username of the user", example = "alice02")
    val username: String,
    @field:ValidPassword
    @Schema(nullable = true, description = "User null for not changing password. Min length 8. Min one digit. Min one lowercase letter. Min one uppercase letter. Min one special character.", example = "Password123@")
    val password: String?
)