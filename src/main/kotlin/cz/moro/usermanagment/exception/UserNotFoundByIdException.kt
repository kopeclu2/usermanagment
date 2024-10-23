package cz.moro.usermanagment.exception


data class UserNotFoundByIdException(
    val id: Long
): Exception("User has not been found by id: $id.")
