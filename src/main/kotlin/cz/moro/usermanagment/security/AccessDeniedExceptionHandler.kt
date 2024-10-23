package cz.moro.usermanagment.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.stereotype.Component
import org.springframework.web.ErrorResponse
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class AccessDeniedExceptionHandler :
    AccessDeniedHandler,
    AuthenticationFailureHandler,
    AuthenticationEntryPoint{
    private val objectMapper = ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {

        val exceptionString = objectMapper.writeValueAsString(
            ErrorResponse.builder(accessDeniedException, HttpStatus.FORBIDDEN, accessDeniedException.message ?: "Unauthorized").build()
        )
        response.status = HttpStatus.FORBIDDEN.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.outputStream.println(exceptionString)
    }

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        val exceptionString = objectMapper.writeValueAsString(
            ErrorResponse.builder(exception, HttpStatus.FORBIDDEN, exception.message ?: "Unauthorized").build()
        )
        response.status = HttpStatus.FORBIDDEN.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.outputStream.println(exceptionString)
    }

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val exceptionString = objectMapper.writeValueAsString(
            ErrorResponse.builder(authException, HttpStatus.FORBIDDEN, authException.message ?: "Unauthorized").build()
        )
        response.status = HttpStatus.FORBIDDEN.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.outputStream.println(exceptionString)
    }
}
