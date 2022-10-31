package com.example.demo.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.Map;
import java.util.TreeMap;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springpOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Spring Boot API")
                                .description("Spring Boot sample application")
                                .version("v0.0.1")
                                .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "authorize",
                                        new SecurityScheme()
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                                .type(SecurityScheme.Type.HTTP)
                                                .in(SecurityScheme.In.HEADER)));
    }

    @Bean
    public OpenApiCustomiser sortSchemasAlphabetically() {
        return openApi -> {
            Map<String, Schema> schemas = openApi.getComponents().getSchemas();
            openApi.getComponents().setSchemas(new TreeMap<>(schemas));
        };
    }
}
