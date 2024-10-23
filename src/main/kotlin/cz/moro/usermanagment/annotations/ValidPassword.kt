package cz.moro.usermanagment.annotations

import cz.moro.usermanagment.validator.PasswordConstraintValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [PasswordConstraintValidator::class])
@Target(FIELD, VALUE_PARAMETER)
@Retention(RUNTIME)
annotation class ValidPassword(
    val message: String = "Password is not valid",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
