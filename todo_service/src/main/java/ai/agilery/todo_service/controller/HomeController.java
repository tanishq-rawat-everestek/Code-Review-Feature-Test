package ai.agilery.todo_service.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Home controller providing basic service information and health check.
 * <p>
 * Provides a simple endpoint to verify the service is running and accessible.
 * </p>
 */
@RestController
@Tag(name = "Home", description = "Basic service information and health check")
public class HomeController {

    /**
     * Returns basic service information.
     *
     * @return ResponseEntity containing service information
     */
    @GetMapping("/")
    @Operation(summary = "Service information", description = "Returns basic information about the todo service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service information retrieved successfully")
    })
    public ResponseEntity<Map<String, String>> home() {
        Map<String, String> response = new HashMap<>();
        response.put("service", "Todo Service");
        response.put("status", "running");
        response.put("version", "1.0.0");
        response.put("description", "API for managing tasks in the todo service");
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint.
     *
     * @return ResponseEntity containing health status
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Simple health check endpoint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service is healthy")
    })
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        return ResponseEntity.ok(response);
    }
}
