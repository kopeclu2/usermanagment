package cz.moro.usermanagment.controller

import cz.moro.usermanagment.controller.RestControllerConstants.USER_REST_CONTROLLER_PATH
import cz.moro.usermanagment.controller.request.UserRequest
import cz.moro.usermanagment.controller.response.UserListResponse
import cz.moro.usermanagment.controller.response.UserResponse
import cz.moro.usermanagment.controller.swagger.UNAUTHORIZED_JSON_RESPONSE
import cz.moro.usermanagment.controller.swagger.USER_NOT_FOUND_RESPONSE
import cz.moro.usermanagment.controller.swagger.VALIDATION_ERRORS_RESPONSE
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Positive
import org.springdoc.core.converters.models.PageableAsQueryParam
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@Tag(name = "Settings", description = "API for Settings management.")
@RequestMapping(USER_REST_CONTROLLER_PATH)
interface UserRestControllerImpl {

    @Operation(summary = "Get user by ID", description = "Returns a user by their ID.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "User found",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = UserResponse::class))]),
        ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content = [
                Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = [
                        ExampleObject(
                            value = UNAUTHORIZED_JSON_RESPONSE,
                        ),
                    ],
                ),
            ],
        ),
        ApiResponse(
            responseCode = "400",
            description = "User not found response",
            content = [
                Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = [
                        ExampleObject(
                            value = USER_NOT_FOUND_RESPONSE,
                        ),
                    ],
                ),
            ],
        )
    ])
    @GetMapping("/{id}")
    fun getUserById(@PathVariable @Valid @Positive id: Long): ResponseEntity<UserResponse>


    @Operation(summary = "Create new user", description = "Create new user.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "User created",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = UserResponse::class))]),
        ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content = [
                Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = [
                        ExampleObject(
                            value = UNAUTHORIZED_JSON_RESPONSE,
                        ),
                    ],
                ),
            ],
        ),
        ApiResponse(
            responseCode = "422",
            description = "Validation errors",
            content = [
                Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = [
                        ExampleObject(
                            value = VALIDATION_ERRORS_RESPONSE,
                        ),
                    ],
                ),
            ],
        ),
    ])
    @PostMapping
    fun createUser(@Valid @RequestBody request: UserRequest): ResponseEntity<UserResponse>

    @Operation(summary = "Update user", description = "Update user.", security = [SecurityRequirement(name = "basicAuth")])
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "User updated",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = UserResponse::class))]),
        ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content = [
                Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = [
                        ExampleObject(
                            value = UNAUTHORIZED_JSON_RESPONSE,
                        ),
                    ],
                ),
            ],
        )
    ])
    @PutMapping("/{id}")
    fun updateUser(@PathVariable("id") @Valid @Positive userId: Long, @Valid @RequestBody request: UserRequest): ResponseEntity<UserResponse>


    @Operation(summary = "Delete user", description = "Delete user.", security = [SecurityRequirement(name = "basicAuth")])
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "User deleted",
            content = [Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = Schema(implementation = String::class), examples = [ExampleObject(value = "OK")])]),
        ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content = [
                Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = [
                        ExampleObject(
                            value = UNAUTHORIZED_JSON_RESPONSE,
                        ),
                    ],
                ),
            ],
        ),
        ApiResponse(
            responseCode = "400",
            description = "User not found response",
            content = [
                Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = [
                        ExampleObject(
                            value = USER_NOT_FOUND_RESPONSE,
                        ),
                    ],
                ),
            ],
        ),
    ])
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable("id") @Valid @Positive userId: Long): ResponseEntity<String>

    @Operation(summary = "Get users", description = "Get users.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Get users",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = UserListResponse::class))]),
        ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content = [
                Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = [
                        ExampleObject(
                            value = UNAUTHORIZED_JSON_RESPONSE,
                        ),
                    ],
                ),
            ],
        ),
    ])
    @GetMapping
    @PageableAsQueryParam
    fun listUsers(@Parameter(hidden = true) pageRequest: Pageable): ResponseEntity<UserListResponse>
}