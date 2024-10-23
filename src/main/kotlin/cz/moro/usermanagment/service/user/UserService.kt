package cz.moro.usermanagment.service.user

import cz.moro.usermanagment.annotations.IsAdminLogged
import cz.moro.usermanagment.controller.request.UserRequest
import cz.moro.usermanagment.controller.response.UserListResponse
import cz.moro.usermanagment.controller.response.UserResponse
import cz.moro.usermanagment.exception.UserNotFoundByIdException
import cz.moro.usermanagment.exception.UsernameAlreadyExistsException
import cz.moro.usermanagment.mapper.UserMapper
import cz.moro.usermanagment.repository.user.UserRepository
import cz.moro.usermanagment.validator.UserValidator
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val userValidator: UserValidator
): UserDetailsService {

    @Throws(UserNotFoundByIdException::class)
    fun getUserById(id: Long) = userRepository.findByIdOrNull(id)
        ?.let { userMapper.mapToResponse(it) }
        ?: throw UserNotFoundByIdException(id)

    @Throws(UsernameAlreadyExistsException::class)
    fun createUser(request: UserRequest): UserResponse {
        userValidator.validateUsernameForCreate(request.username)

        val userToSave = userMapper.mapInputToEntity(request)

        val savedUser = userRepository.save(userToSave)

        return userMapper.mapToResponse(savedUser)
    }

    @Throws(UserNotFoundByIdException::class)
    @IsAdminLogged
    fun editUser(request: UserRequest, id: Long): UserResponse {
        // Example with scope functions
        return userRepository.findByIdOrNull(id)
             ?.also { userValidator.validateUsernameForEdit(it, request.username) }
             ?.let { userMapper.edit(it, request) }
             ?.let { userRepository.save(it) }
             ?.let { userMapper.mapToResponse(it) }
             ?: throw UserNotFoundByIdException(id)
    }

    @Throws(UserNotFoundByIdException::class)
    @IsAdminLogged
    fun deleteUser(id: Long) {
        userRepository.findByIdOrNull(id)
            ?: throw UserNotFoundByIdException(id)

        userRepository.deleteById(id)
    }

    fun listUsers(pageRequest: Pageable): UserListResponse {
        val page = userRepository.findAll(pageRequest)

        return userMapper.mapToResponse(page)
    }


    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByUsername(username)
            ?.let(userMapper::mapToUserDetails)
            ?: throw UsernameNotFoundException("User has not been found by username: $username")
    }

}