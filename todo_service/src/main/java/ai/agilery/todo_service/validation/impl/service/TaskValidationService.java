package ai.agilery.todo_service.validation.impl.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.agilery.todo_service.dto.TaskDto;
import ai.agilery.todo_service.dto.ValidationErrorDto;
import ai.agilery.todo_service.validation.AbstractValidationService;
import ai.agilery.todo_service.validation.impl.chain.TaskCreateValidationChain;
import ai.agilery.todo_service.validation.impl.chain.TaskDeleteValidationChain;
import ai.agilery.todo_service.validation.impl.chain.TaskGetAllValidationChain;
import ai.agilery.todo_service.validation.impl.chain.TaskGetValidationChain;
import ai.agilery.todo_service.validation.impl.chain.TaskPatchValidationChain;
import ai.agilery.todo_service.validation.impl.chain.TaskUpdateValidationChain;
import ai.agilery.todo_service.validation.util.CommonValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for validating task operations.
 * <p>
 * This service provides validation for all CRUD operations on tasks,
 * including create, update, patch, get, getAll, and delete operations.
 * It uses validation chains to perform semantic validation and throws
 * appropriate exceptions when validation fails.
 * </p>
 */
@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class TaskValidationService implements AbstractValidationService<TaskDto, UUID> {

    /**
     * Validation chain for task creation.
     */
    private final TaskCreateValidationChain taskCreateValidationChain;

    /**
     * Validation chain for task updates.
     */
    private final TaskUpdateValidationChain taskUpdateValidationChain;

    /**
     * Validation chain for task patches.
     */
    private final TaskPatchValidationChain taskPatchValidationChain;

    /**
     * Validation chain for retrieving a single task.
     */
    private final TaskGetValidationChain taskGetValidationChain;

    /**
     * Validation chain for retrieving all tasks.
     */
    private final TaskGetAllValidationChain taskGetAllValidationChain;

    /**
     * Validation chain for task deletion.
     */
    private final TaskDeleteValidationChain taskDeleteValidationChain;

    /**
     * Utility for common validation operations.
     */
    private final CommonValidationUtil commonValidationUtil;

    /**
     * Validates a task before creation.
     *
     * @param taskDto the task DTO to validate
     * @throws ai.agilery.todo_service.exception.ValidationException if validation fails
     */
    @Override
    public void onCreate(TaskDto taskDto) {
        log.info("onCreate validation for TaskDto: {}", taskDto);
        List<ValidationErrorDto> errors = taskCreateValidationChain.validate(taskDto);
        commonValidationUtil.throwValidationException(errors);
        log.info("onCreate validation passed for TaskDto: {}", taskDto);
    }

    /**
     * Validates a task before update.
     *
     * @param taskDto the task DTO to validate
     * @throws ai.agilery.todo_service.exception.ValidationException if validation fails
     */
    @Override
    public void onUpdate(TaskDto taskDto) {
        log.info("onUpdate validation for TaskDto ID: {}", taskDto.getId());
        List<ValidationErrorDto> errors = taskUpdateValidationChain.validate(taskDto);
        commonValidationUtil.throwValidationException(errors);
        log.info("onUpdate validation passed for TaskDto ID: {}", taskDto.getId());
    }

    /**
     * Validates a task before patch.
     *
     * @param taskDto the task DTO to validate
     * @throws ai.agilery.todo_service.exception.ValidationException if validation fails
     */
    @Override
    public void onPatch(TaskDto taskDto) {
        log.info("onPatch validation for TaskDto ID: {}", taskDto.getId());
        List<ValidationErrorDto> errors = taskPatchValidationChain.validate(taskDto);
        commonValidationUtil.throwValidationException(errors);
        log.info("onPatch validation passed for TaskDto ID: {}", taskDto.getId());
    }

    /**
     * Validates before retrieving a task by ID.
     *
     * @param id the ID of the task to retrieve
     * @throws ai.agilery.todo_service.exception.ResourceNotFoundException if task not found
     */
    @Override
    public void onGet(UUID id) {
        log.info("onGet validation for Task ID: {}", id);
        List<ValidationErrorDto> errors = taskGetValidationChain.validate(id);
        commonValidationUtil.throwResourceNotFoundException(errors, "Task with ID '%s' not found", id);
        log.info("onGet validation passed for Task ID: {}", id);
    }

    /**
     * Validates before retrieving all tasks.
     *
     * @param pageable the pagination parameters
     * @throws ai.agilery.todo_service.exception.ValidationException if validation fails
     */
    @Override
    public void onGetAll(Pageable pageable) {
        log.info("onGetAll validation for Pageable: {}", pageable);
        List<ValidationErrorDto> errors = taskGetAllValidationChain.validate(pageable);
        commonValidationUtil.throwValidationException(errors);
        log.info("onGetAll validation passed for Pageable: {}", pageable);
    }

    /**
     * Validates before deleting a task.
     *
     * @param id the ID of the task to delete
     * @throws ai.agilery.todo_service.exception.ValidationException if validation fails
     */
    public void onDelete(UUID id) {
        log.info("onDelete validation for Task ID: {}", id);
        List<ValidationErrorDto> errors = taskDeleteValidationChain.validate(id);
        commonValidationUtil.throwValidationException(errors);
        log.info("onDelete validation passed for Task ID: {}", id);
    }
}
