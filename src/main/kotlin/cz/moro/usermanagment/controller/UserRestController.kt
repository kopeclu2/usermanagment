package cz.moro.usermanagment.controller

import cz.moro.usermanagment.controller.request.UserRequest
import cz.moro.usermanagment.controller.response.UserListResponse
import cz.moro.usermanagment.controller.response.UserResponse
import cz.moro.usermanagment.controller.swagger.UNAUTHORIZED_JSON_RESPONSE
import cz.moro.usermanagment.controller.swagger.USER_NOT_FOUND_RESPONSE
import cz.moro.usermanagment.service.user.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController("UserControllerV1")
@Validated
class UserControllerImpl2(private val userService: UserService): UserRestControllerImpl {

    override  fun getUserById(id: Long): ResponseEntity<UserResponse> {
        val response = userService.getUserById(id)
        return ResponseEntity.ok(response)
    }

    override  fun createUser(request: UserRequest): ResponseEntity<UserResponse> {
        val response = userService.createUser(request)
        return ResponseEntity.ok(response)
    }

    override  fun updateUser(userId: Long, request: UserRequest): ResponseEntity<UserResponse> {
        val response = userService.editUser(request, userId)
        return ResponseEntity.ok(response)
    }

    override  fun deleteUser(userId: Long): ResponseEntity<String> {
        userService.deleteUser(userId)
        return ResponseEntity.ok("OK")
    }

    override fun listUsers(pageRequest: Pageable): ResponseEntity<UserListResponse> {
        val response = userService.listUsers(pageRequest)
        return ResponseEntity.ok(response)
    }

    override fun me(authentication: Authentication): ResponseEntity<String> {
        return ResponseEntity.ok((authentication.principal as User).username)
    }


}