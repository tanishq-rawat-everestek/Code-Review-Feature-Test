package ai.agilery.todo_service.validation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ai.agilery.todo_service.dto.ValidationErrorDto;
import ai.agilery.todo_service.entity.Task;
import ai.agilery.todo_service.entity.TaskStatus;
import ai.agilery.todo_service.repository.TaskRepository;
import ai.agilery.todo_service.validation.ValidationHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Validator to ensure a task can be deleted based on its current status.
 * <p>
 * This validator checks if the task's status allows deletion.
 * Only tasks with status PENDING or CANCELLED can be deleted.
 * </p>
 */
@Slf4j
@RequiredArgsConstructor
public class TaskDeletionStatusValidator implements ValidationHandler<UUID> {

    /**
     * Repository for accessing task data.
     */
    private final TaskRepository taskRepository;

    /**
     * Validates whether a task can be deleted based on its status.
     *
     * @param taskId the ID of the task to validate
     * @return list of validation errors, empty if validation passes
     */
    @Override
    public List<ValidationErrorDto> validate(UUID taskId) {
        List<ValidationErrorDto> errors = new ArrayList<>();

        if (taskId == null) {
            errors.add(buildError("taskId", "id", "Task ID cannot be null for deletion"));
            return errors;
        }

        Optional<Task> existingTaskOpt = taskRepository.findById(taskId);

        if (existingTaskOpt.isEmpty()) {
            return errors;
        }

        TaskStatus currentStatus = existingTaskOpt.get().getStatus();

        if (currentStatus != TaskStatus.PENDING && currentStatus != TaskStatus.CANCELLED) {
            log.warn("Validation failed: Task with ID {} cannot be deleted with status {}", taskId, currentStatus);
            errors.add(buildError("taskId", "id", 
                       String.format("Task cannot be deleted with status '%s'. Only PENDING or CANCELLED tasks can be deleted.", currentStatus)));
        }

        return errors;
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
