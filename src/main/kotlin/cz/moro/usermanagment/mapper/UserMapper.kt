package cz.moro.usermanagment.mapper

import cz.moro.usermanagment.controller.request.UserRequest
import cz.moro.usermanagment.controller.response.PageInfoResponse
import cz.moro.usermanagment.controller.response.UserListResponse
import cz.moro.usermanagment.controller.response.UserResponse
import cz.moro.usermanagment.repository.user.UserEntity
import cz.moro.usermanagment.repository.user.UserRole
import org.springframework.data.domain.Page
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserMapper(
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {

    fun mapToResponse(entity: UserEntity): UserResponse {
        return UserResponse(
            id = entity.id ?: error("Id should be not null"),
            name = entity.name,
            userName = entity.username
        )
    }

    fun mapToResponse(page: Page<UserEntity>): UserListResponse {
        return UserListResponse(
            data = page.content.map { mapToResponse(it) },
            pageInfo = PageInfoResponse(
                total = page.totalElements,
                pageSize = page.pageable.pageSize.toLong(),
                page = page.pageable.pageNumber.toLong()
            )
        )
    }

    fun mapInputToEntity(request: UserRequest): UserEntity {
        return UserEntity(
            name = request.name,
            username = request.username,
            password = bCryptPasswordEncoder.encode(request.password),
            userRole = UserRole.USER
        )
    }

    fun edit(existingUser: UserEntity, request: UserRequest): UserEntity {
        return existingUser.copy(
            name = request.name,
            username = request.username,
            password = request.password?.let { bCryptPasswordEncoder.encode(it) } ?: existingUser.password
        )
    }

    fun mapToUserDetails(userEntity: UserEntity): UserDetails {
        return User.builder()
            .username(userEntity.username)
            .password(userEntity.password)
            .roles(userEntity.userRole.name)
            .build();
    }
}