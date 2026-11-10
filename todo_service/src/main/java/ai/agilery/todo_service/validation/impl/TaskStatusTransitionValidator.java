package ai.agilery.todo_service.validation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ai.agilery.todo_service.dto.TaskDto;
import ai.agilery.todo_service.dto.ValidationErrorDto;
import ai.agilery.todo_service.entity.Task;
import ai.agilery.todo_service.entity.TaskStatus;
import ai.agilery.todo_service.repository.TaskRepository;
import ai.agilery.todo_service.validation.ValidationHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Validator to ensure valid task status transitions.
 * <p>
 * This validator checks if the status transition from the current status
 * to the new status is allowed based on business rules.
 * </p>
 * <p>
 * Valid transitions:
 * <ul>
 *   <li>PENDING → IN_PROGRESS, CANCELLED</li>
 *   <li>IN_PROGRESS → COMPLETED, CANCELLED</li>
 *   <li>COMPLETED → (no transitions allowed)</li>
 *   <li>CANCELLED → (no transitions allowed)</li>
 * </ul>
 * </p>
 */
@Slf4j
@RequiredArgsConstructor
public class TaskStatusTransitionValidator implements ValidationHandler<TaskDto> {

    /**
     * Repository for accessing task data.
     */
    private final TaskRepository taskRepository;

    /**
     * Validates the status transition for a task.
     *
     * @param taskDto the task DTO containing the new status
     * @return list of validation errors, empty if validation passes
     */
    @Override
    public List<ValidationErrorDto> validate(TaskDto taskDto) {
        List<ValidationErrorDto> errors = new ArrayList<>();

        if (taskDto.getId() == null || taskDto.getStatus() == null) {
            return errors;
        }

        Optional<Task> existingTaskOpt = taskRepository.findById(taskDto.getId());

        if (existingTaskOpt.isEmpty()) {
            return errors;
        }

        TaskStatus currentStatus = existingTaskOpt.get().getStatus();
        TaskStatus newStatus = TaskStatus.valueOf(taskDto.getStatus());

        if (currentStatus.equals(newStatus)) {
            return errors;
        }

        if (!isValidTransition(currentStatus, newStatus)) {
            log.warn("Validation failed: Invalid task status transition from {} to {} for Task ID {}", 
                     currentStatus, newStatus, taskDto.getId());
            errors.add(buildError("taskDto", "status", 
                       String.format("Invalid status transition from '%s' to '%s'", currentStatus, newStatus)));
        }

        return errors;
    }

    /**
     * Checks if the status transition is valid.
     *
     * @param currentStatus the current status of the task
     * @param newStatus the new status to transition to
     * @return true if the transition is valid, false otherwise
     */
    private boolean isValidTransition(TaskStatus currentStatus, TaskStatus newStatus) {
        switch (currentStatus) {
            case PENDING:
                return newStatus == TaskStatus.IN_PROGRESS || newStatus == TaskStatus.CANCELLED;
            case IN_PROGRESS:
                return newStatus == TaskStatus.COMPLETED || newStatus == TaskStatus.CANCELLED;
            case COMPLETED:
            case CANCELLED:
                return false;
            default:
                return false;
        }
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
