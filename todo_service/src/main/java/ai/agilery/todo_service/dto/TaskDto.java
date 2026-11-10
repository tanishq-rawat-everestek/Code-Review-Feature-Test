package ai.agilery.todo_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import ai.agilery.todo_service.entity.TaskPriority;
import ai.agilery.todo_service.entity.TaskStatus;
import ai.agilery.todo_service.enumeration.ValueOfEnum;
import ai.agilery.todo_service.validation.NullOrNotEmpty;
import ai.agilery.todo_service.validation.OnCreate;
import ai.agilery.todo_service.validation.OnPatch;
import ai.agilery.todo_service.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Task entity.
 * Used for creating, updating, and retrieving task information.
 */
@Data
@Schema(description = "Details of a task, including title, description, status, priority, and due date.")
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    /**
     * Minimum length for title field.
     */
    private static final int LEN_2 = 2;

    /**
     * Maximum length for title field.
     */
    private static final int LEN_200 = 200;

    /**
     * Maximum length for description field.
     */
    private static final int LEN_1000 = 1000;

    /**
     * Unique identifier for the task.
     */
    @JsonProperty(access = Access.READ_ONLY)
    @Schema(description = "Unique identifier for the task.", accessMode = Schema.AccessMode.READ_ONLY, example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    /**
     * Title of the task.
     */
    @Schema(description = "The title of the task.", example = "Complete project documentation")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Title is required")
    @NullOrNotEmpty(groups = OnPatch.class)
    @Size(min = LEN_2, max = LEN_200, groups = {OnCreate.class, OnUpdate.class, OnPatch.class}, message = "Title must be between 2 and 200 characters")
    private String title;

    /**
     * Detailed description of the task.
     */
    @Schema(description = "Detailed description of the task.", example = "Write comprehensive documentation for the REST API endpoints")
    @NullOrNotEmpty(groups = OnPatch.class)
    @Size(max = LEN_1000, groups = {OnCreate.class, OnUpdate.class, OnPatch.class}, message = "Description cannot exceed 1000 characters")
    private String description;

    /**
     * Current status of the task.
     */
    @Schema(description = "Current status of the task.", example = "PENDING")
    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Status is required")
    @ValueOfEnum(enumClass = TaskStatus.class, groups = {OnCreate.class, OnUpdate.class, OnPatch.class}, message = "Invalid task status")
    @NullOrNotEmpty(groups = OnPatch.class)
    private String status;

    /**
     * Priority level of the task.
     */
    @Schema(description = "Priority level of the task.", example = "HIGH")
    @ValueOfEnum(enumClass = TaskPriority.class, groups = {OnCreate.class, OnUpdate.class, OnPatch.class}, message = "Invalid task priority")
    @NullOrNotEmpty(groups = OnPatch.class)
    private String priority;

    /**
     * Due date for the task.
     */
    @Schema(description = "Due date for the task.", example = "2024-12-31T23:59:59")
    private LocalDateTime dueDate;

    /**
     * Indicates whether the task is completed.
     */
    @JsonProperty(access = Access.READ_ONLY)
    @Schema(description = "Indicates whether the task is completed.", accessMode = Schema.AccessMode.READ_ONLY, example = "false")
    private boolean completed;

    /**
     * Timestamp when the task was created.
     */
    @JsonProperty(access = Access.READ_ONLY)
    @Schema(description = "Timestamp when the task was created.", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdDateTime;

    /**
     * Timestamp when the task was last modified.
     */
    @JsonProperty(access = Access.READ_ONLY)
    @Schema(description = "Timestamp when the task was last modified.", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime lastModifiedDateTime;

    /**
     * ID of the user who created the task.
     */
    @JsonProperty(access = Access.READ_ONLY)
    @Schema(description = "ID of the user who created the task.", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID createdBy;

    /**
     * ID of the user who last modified the task.
     */
    @JsonProperty(access = Access.READ_ONLY)
    @Schema(description = "ID of the user who last modified the task.", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID modifiedBy;
}


