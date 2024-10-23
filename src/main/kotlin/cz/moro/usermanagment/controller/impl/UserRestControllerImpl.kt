package cz.moro.usermanagment.controller.impl

import cz.moro.usermanagment.controller.UserRestController
import cz.moro.usermanagment.controller.request.UserRequest
import cz.moro.usermanagment.controller.response.UserListResponse
import cz.moro.usermanagment.controller.response.UserResponse
import cz.moro.usermanagment.service.user.UserService
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RestController

@RestController("UserControllerV1")
@Validated
class UserRestControllerImpl(private val userService: UserService): UserRestController {

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