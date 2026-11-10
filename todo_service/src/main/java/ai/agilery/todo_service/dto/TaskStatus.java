package ai.agilery.todo_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enumeration representing the current status of a task.
 *
 * @deprecated This enum is deprecated and should not be used.
 *             Use {@link ai.agilery.todo_service.entity.TaskStatus} instead.
 *             This duplicate definition violates DRY principle and has been
 *             consolidated into the entity package.
 */
@Deprecated(since = "1.0", forRemoval = true)
@Schema(description = "Represents the current status of a task.")
public enum TaskStatus {
    /**
     * Task is pending and not yet started.
     */
    PENDING,

    /**
     * Task is currently in progress.
     */
    IN_PROGRESS,

    /**
     * Task has been completed.
     */
    COMPLETED,

    /**
     * Task has been cancelled.
     */
    CANCELLED
}


