package cz.moro.usermanagment.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class PasswordEncoderConfiguration {

    private companion object {
        const val BCRYPT_STRENGTH = 16
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder(BCRYPT_STRENGTH)
}