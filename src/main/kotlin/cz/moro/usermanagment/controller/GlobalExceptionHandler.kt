package cz.moro.usermanagment.controller

import cz.moro.usermanagment.controller.request.UserRequest
import cz.moro.usermanagment.controller.response.ValidationPathError
import cz.moro.usermanagment.exception.UserNotFoundByIdException
import cz.moro.usermanagment.exception.UsernameAlreadyExistsException
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import java.util.stream.Collectors


@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [UserNotFoundByIdException::class])
    @ResponseBody
    fun handleResourceNotFoundException(ex: UserNotFoundByIdException): ErrorResponse {
        return ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, ex.message ?: "Something went wrong.")
            .property("errorCode", "USER_NOT_FOUND")
            .build()
    }

    @ExceptionHandler(value = [UsernameAlreadyExistsException::class])
    @ResponseBody
    // TODO This can be also ConstraintValidator !!
    fun handleUsernameAlreadyExists(ex: UsernameAlreadyExistsException): ErrorResponse {
       return ErrorResponse.builder(ex, HttpStatus.UNPROCESSABLE_ENTITY, ex.message ?: "Something went wrong.")
            .property("errors", listOf(
                ValidationPathError(
                    path = UserRequest::username.name,
                    message = ex.message ?: "Non unique value.",
                    value = ex.value,
                    objectName = null
                )
            ))
            .build()
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationErrors(ex: MethodArgumentNotValidException): ErrorResponse {
        return ErrorResponse.builder(ex, HttpStatus.UNPROCESSABLE_ENTITY, ex.message)
            .property("errors", ex.bindingResult.fieldErrors
                .map {
                    ValidationPathError(
                        path = it.field,
                        message = it.defaultMessage ?: "",
                        value = it.rejectedValue,
                        objectName = it.objectName
                    )
                })
            .build()
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleValidationErrors(ex: ConstraintViolationException): ErrorResponse {
        return ErrorResponse.builder(ex, HttpStatus.UNPROCESSABLE_ENTITY, ex.message ?: "Validation error.")
            .property("errors", ex.constraintViolations
                .map {
                    ValidationPathError(
                        path = it.propertyPath.toString(),
                        message = it.message,
                        value = it.invalidValue,
                        objectName = it.constraintDescriptor.annotation.toString()
                    )
                })
            .build()
    }
}