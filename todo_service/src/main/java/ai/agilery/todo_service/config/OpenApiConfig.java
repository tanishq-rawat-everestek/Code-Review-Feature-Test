package ai.agilery.todo_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for OpenAPI/Swagger documentation.
 * <p>
 * Defines the API metadata including title, description, and version
 * that will be displayed in the Swagger UI.
 * </p>
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configures the OpenAPI documentation with service information.
     *
     * @return OpenAPI instance with configured metadata
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Todo service")
                        .description("API documentation for Todo service")
                        .version("1.0.0"));
    }
}
