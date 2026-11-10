package ai.agilery.todo_service;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;

/**
 * The main entry point for the Todo Service Application.
 * <p>
 * This class initializes and configures the Spring Boot application. It
 * includes configuration for component scanning and Feign clients.
 * </p>
 * <ul>
 * <li>@SpringBootApplication: Marks this as a Spring Boot application.</li>
 * <li>@ComponentScan: Specifies the base packages to scan for Spring
 * components.</li>
 * <li>@EnableFeignClients: Enables Feign clients for declarative REST client
 * implementation.</li>
 * </ul>
 */
@SpringBootApplication(nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class)
@EnableFeignClients
public class TodoServiceApplication extends SpringBootServletInitializer {

    // Set time zone of the service to UTC
    static {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(final String[] args) {
        SpringApplication.run(TodoServiceApplication.class, args);
    }

    /**
     * Configures the application when deployed in a servlet container.
     *
     * @param builder the SpringApplicationBuilder used to configure the application
     * @return the configured SpringApplicationBuilder
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }
}
