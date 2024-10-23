package cz.moro.usermanagment.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringdocConfig {

    private companion object {
        const val SCHEME_NAME = "basicAuth"
        const val SCHEME = "basic"
    }


    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .components(
                Components()
                    .addSecuritySchemes(SCHEME_NAME, createSecurityScheme())
            )
    }

    private fun createSecurityScheme(): SecurityScheme {
        return SecurityScheme()
            .name(SCHEME_NAME)
            .type(SecurityScheme.Type.HTTP)
            .scheme(SCHEME)
    }
}