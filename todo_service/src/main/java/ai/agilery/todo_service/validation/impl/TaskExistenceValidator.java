package ai.agilery.todo_service.validation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ai.agilery.todo_service.dto.TaskDto;
import ai.agilery.todo_service.dto.ValidationErrorDto;
import ai.agilery.todo_service.entity.Task;
import ai.agilery.todo_service.repository.TaskRepository;
import ai.agilery.todo_service.validation.ValidationHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Validator to check if a task exists or does not exist in the database.
 * <p>
 * This validator can be configured to either:
 * <ul>
 *   <li>Ensure a task exists (for update/patch/delete operations)</li>
 *   <li>Ensure a task does not exist (for create operations, if needed)</li>
 * </ul>
 * </p>
 */
@Slf4j
@RequiredArgsConstructor
public class TaskExistenceValidator<T> implements ValidationHandler<T> {

    /**
     * Repository for accessing task data.
     */
    private final TaskRepository taskRepository;

    /**
     * Flag indicating whether the task must exist (true) or must not exist (false).
     */
    private final boolean mustExist;

    /**
     * Validates whether a task exists or does not exist based on the configuration.
     *
     * @param data the data to validate (either TaskDto or UUID)
     * @return list of validation errors, empty if validation passes
     */
    @Override
    public List<ValidationErrorDto> validate(T data) {
        List<ValidationErrorDto> errors = new ArrayList<>();

        UUID taskId = extractTaskId(data);

        if (taskId == null) {
            if (mustExist) {
                errors.add(buildError("taskDto", "id", "Task ID cannot be null"));
            }
            return errors;
        }

        Optional<Task> existingTask = taskRepository.findById(taskId);

        if (mustExist && existingTask.isEmpty()) {
            log.warn("Validation failed: Task with ID {} not found", taskId);
            errors.add(buildError("taskDto", "id", String.format("Task with ID '%s' not found!", taskId)));
        }

        if (!mustExist && existingTask.isPresent()) {
            log.warn("Validation failed: Task with ID {} already exists", taskId);
            errors.add(buildError("taskDto", "id", String.format("Task with ID '%s' already exists!", taskId)));
        }

        return errors;
    }

    /**
     * Extracts the task ID from the provided data.
     *
     * @param data the data object (TaskDto or UUID)
     * @return the extracted task ID, or null if not available
     */
    private UUID extractTaskId(T data) {
        if (data instanceof TaskDto) {
            return ((TaskDto) data).getId();
        } else if (data instanceof UUID) {
            return (UUID) data;
        }
        return null;
    }

    /**
     * Builds a validation error DTO.
     *
     * @param objectName the name of the object being validated
     * @param field the field that failed validation
     * @param message the error message
     * @return a ValidationErrorDto instance
     */
    private ValidationErrorDto buildError(String objectName, String field, String message) {
        ValidationErrorDto error = new ValidationErrorDto();
        error.setObjectName(objectName);
        error.setField(field);
        error.setMessage(message);
        return error;
    }
}
