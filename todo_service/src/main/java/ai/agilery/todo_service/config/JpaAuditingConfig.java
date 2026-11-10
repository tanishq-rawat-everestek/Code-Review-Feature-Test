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
     * System UUID constant for services without authentication.
     */
    private static final UUID SYSTEM_USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    /**
     * Provides the current auditor (user) for auditing fields.
     * <p>
     * Returns a system UUID for this service. This implementation is appropriate for
     * services that do not have user authentication. In applications with authentication,
     * this would typically retrieve the ID of the currently logged-in user from the
     * security context (e.g., {@code SecurityContextHolder.getContext().getAuthentication()}).
     * </p>
     *
     * @return AuditorAware bean that supplies the current auditor's ID
     */
    @Bean
    public AuditorAware<UUID> auditorProvider() {
        return () -> Optional.of(SYSTEM_USER_ID);
    }
}
