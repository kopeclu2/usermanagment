package cz.moro.usermanagment.validator

import cz.moro.usermanagment.controller.request.UserRequest
import cz.moro.usermanagment.exception.UsernameAlreadyExistsException
import cz.moro.usermanagment.repository.user.UserEntity
import cz.moro.usermanagment.repository.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserValidator(private val userRepository: UserRepository) {

    fun validateUsernameForCreate(username: String) {
        if (userRepository.existsByUsername(username)) {
            throw UsernameAlreadyExistsException("Username '$username' already exists.", username)
        }
    }

    fun validateUsernameForEdit(existingUser: UserEntity, newUsername: String) {
        if (existingUser.username != newUsername) {
            val existsByUsername = userRepository.existsByUsername(newUsername)
            if (existsByUsername) {
                throw UsernameAlreadyExistsException("Username '$newUsername' already exists.", newUsername)
            }
        }
    }
}