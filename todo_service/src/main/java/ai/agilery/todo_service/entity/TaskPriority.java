package ai.agilery.todo_service.entity;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enumeration representing the priority level of a task.
 *
 * <p>Defines the importance or urgency of a task:</p>
 * <ul>
 *   <li>LOW - Task has low priority</li>
 *   <li>MEDIUM - Task has medium priority</li>
 *   <li>HIGH - Task has high priority</li>
 *   <li>CRITICAL - Task is critical and requires immediate attention</li>
 * </ul>
 */
@Schema(description = "Represents the priority level of a task.")
public enum TaskPriority {
    /**
     * Task has low priority.
     */
    LOW,

    /**
     * Task has medium priority.
     */
    MEDIUM,

    /**
     * Task has high priority.
     */
    HIGH,

    /**
     * Task is critical and requires immediate attention.
     */
    CRITICAL
}
