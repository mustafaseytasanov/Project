package com.example.project.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Class OpenApiConfig.
 * @author Mustafa
 * @version 1.0
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Application",
                description = "Rest Api for server of statistics",
                version = "1.0.0",
                contact = @Contact(
                        name = "Seitasanov Mustafa",
                        email = "must99@inbox.ru",
                        url = "https://t.me/mustafa_seytasanov"
                )
        )
)
public class OpenApiConfig {
    // Configuration for Swagger
}
