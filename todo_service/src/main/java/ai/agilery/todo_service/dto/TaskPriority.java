package ai.agilery.todo_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enumeration representing the priority level of a task.
 *
 * @deprecated This enum is deprecated and should not be used.
 *             Use {@link ai.agilery.todo_service.entity.TaskPriority} instead.
 *             This duplicate definition violates DRY principle and has been
 *             consolidated into the entity package.
 */
@Deprecated(since = "1.0", forRemoval = true)
@Schema(description = "Represents the priority level of a task.")
public enum TaskPriority {
    /**
     * Low priority task.
     */
    LOW,

    /**
     * Medium priority task.
     */
    MEDIUM,

    /**
     * High priority task.
     */
    HIGH,

    /**
     * Critical priority task requiring immediate attention.
     */
    CRITICAL
}


