package ai.agilery.todo_service.entity;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enumeration representing the status of a task.
 *
 * <p>Defines the lifecycle states a task can be in:</p>
 * <ul>
 *   <li>PENDING - Task is created but not yet started</li>
 *   <li>IN_PROGRESS - Task is currently being worked on</li>
 *   <li>COMPLETED - Task has been finished</li>
 *   <li>CANCELLED - Task has been cancelled and will not be completed</li>
 * </ul>
 */
@Schema(description = "Represents the current status of a task.")
public enum TaskStatus {
    /**
     * Task is created but not yet started.
     */
    PENDING,

    /**
     * Task is currently being worked on.
     */
    IN_PROGRESS,

    /**
     * Task has been finished.
     */
    COMPLETED,

    /**
     * Task has been cancelled and will not be completed.
     */
    CANCELLED
}
