package com.game.rockpaperscissor.annotations;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to add common API responses for methods or classes.
 * Use this to avoid repeating these common responses in your API documentation.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid input", ref = "#/components/responses/IllegalArgument"),
        @ApiResponse(responseCode = "404", description = "Game not found", ref = "#/components/responses/GameNotFound"),
        @ApiResponse(responseCode = "200", description = "Request processed successfully"),
})
public @interface ApiResponsesCommon {
}