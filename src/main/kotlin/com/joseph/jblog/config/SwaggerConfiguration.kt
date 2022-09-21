package com.joseph.jblog.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import java.util.*

@Configuration
class SwaggerConfiguration {
    //Configure api documentation instance
    val AUTHORIZATION_HEADER = "Authorization"
    private fun apiKey(): ApiKey {
        return ApiKey("JWT", AUTHORIZATION_HEADER, "header")
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfo(
            "Kithome Prayer Application |APi",
            "Spring Boot Prayer API Documentation",
            "v1",
            "Terms of Service",
            Contact("Kithome Joseph", "https://joseprofile.herokuapp.com", "jmulingwakithome.jmk@gmail.com"),
            "License of API",
            "LICENCE OF API URL", emptyList()
        )
    }

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf<SecurityScheme>(apiKey()))
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
    }

    private fun securityContext(): SecurityContext {
        return SecurityContext.builder().securityReferences(defaultAuth()).build()
    }

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return listOf(SecurityReference("JWT", authorizationScopes))
    }
}