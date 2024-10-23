package cz.moro.usermanagment.security;

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationSuccessHandler: SimpleUrlAuthenticationSuccessHandler() {

    private val log = LoggerFactory.getLogger(this::class.java)
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        log.info("User $authentication has been logged in.")
        super.onAuthenticationSuccess(request, response, authentication)
    }
}
