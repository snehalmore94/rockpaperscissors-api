package com.game.rockpaperscissor.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up OpenAPI documentation.
 * This class defines the components that are referenced by the ApiResponsesCommon annotation.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addResponses("Success", new ApiResponse().description("Request processed successfully"))
                        .addResponses("GameNotFound", new ApiResponse().description("Game not found"))
                        .addResponses("GameFinished", new ApiResponse().description("The game is already finished"))
                        .addResponses("IllegalArgument", new ApiResponse().description("Invalid input"))
                )
                .info(new Info()
                        .title("Rock Paper Scissor API")
                        .version("1.0")
                        .description("API documentation for the Rock Paper Scissor game"));
    }
}