package com.joseph.jblog.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConf {
    //Configure api documentation instance

    public final String AUTHORIZATION_HEADER = "Authorization";
    private ApiKey apiKey(){
        return  new ApiKey("JWT",AUTHORIZATION_HEADER,"header");
    }
    private ApiInfo apiInfo(){
        return new ApiInfo(
                "Kithome Prayer Application |APi",
                "Spring Boot Prayer API Documentation",
                "v1",
                "Terms of Service",
                new Contact("Kithome Joseph","https://joseprofile.herokuapp.com", "jmulingwakithome.jmk@gmail.com"),
                "License of API",
                "LICENCE OF API URL",
                Collections.emptyList()

        );
    }

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
    private SecurityContext securityContext(){
        return  SecurityContext.builder().securityReferences(defaultAuth()).build();
    }
    private List<SecurityReference> defaultAuth(){
        springfox.documentation.service.AuthorizationScope authorizationScope = new springfox.documentation.service.AuthorizationScope("global", "accessEverything");
        springfox.documentation.service.AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
}
