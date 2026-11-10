package ai.agilery.todo_service.config;

import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration class for JPA auditing support.
 * <p>
 * Enables automatic population of {@code @CreatedBy} and {@code @LastModifiedBy}
 * fields in JPA entities through the {@link AuditorAware} interface.
 * </p>
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {

    /**
     * Provides the current auditor (user) for auditing fields.
     * <p>
     * Currently returns a system UUID. In a production application with authentication,
     * this should return the ID of the currently logged-in user from the security context.
     * </p>
     *
     * @return AuditorAware bean that supplies the current auditor's ID
     */
    @Bean
    public AuditorAware<UUID> auditorProvider() {
        return () -> {
            // TODO: In production, retrieve current user from SecurityContext
            // Example: SecurityContextHolder.getContext().getAuthentication()...
            // For now, return a system UUID
            return Optional.of(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        };
    }
}
