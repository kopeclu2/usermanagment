package cz.moro.usermanagment.security

import cz.moro.usermanagment.controller.RestControllerConstants.GET_BY_ID_USER_REST_CONTROLLER_PATH
import cz.moro.usermanagment.controller.RestControllerConstants.USER_REST_CONTROLLER_PATH
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher


@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val accessDeniedExceptionHandler: AccessDeniedExceptionHandler
) {


    @Bean("BasicAuthenticationProvider")
    fun userDetailsService(
        passwordEncoder: BCryptPasswordEncoder,
        userDetailsService: UserDetailsService
    ): ProviderManager {
        return ProviderManager(
            DaoAuthenticationProvider().apply {
                setUserDetailsService(userDetailsService)
                setPasswordEncoder(passwordEncoder)
            }
        )
    }

    @Bean
    fun securityWebFilterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf { it.disable() }
            .cors { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .httpBasic { }
            .formLogin { it.disable() }
            .authorizeHttpRequests {
                // Permit access to Swagger UI and API docs
                it.requestMatchers(
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html"
                ).permitAll()
                    // Allow GET requests for user resources
                    .requestMatchers(HttpMethod.GET, USER_REST_CONTROLLER_PATH).permitAll()
                    // Allow GET requests for getting user by ID
                    .requestMatchers(HttpMethod.GET, GET_BY_ID_USER_REST_CONTROLLER_PATH).permitAll()
                    // Allow POST requests for creating users
                    .requestMatchers(HttpMethod.POST, USER_REST_CONTROLLER_PATH).permitAll()
                    // Any other request needs to be authenticated
                    .anyRequest().authenticated()
            }
            .exceptionHandling {
                it.authenticationEntryPoint(accessDeniedExceptionHandler)
                it.accessDeniedHandler(accessDeniedExceptionHandler)
            }
            .build()
}