package ai.agilery.todo_service.validation.impl.chain;

import org.springframework.stereotype.Component;

import ai.agilery.todo_service.dto.TaskDto;
import ai.agilery.todo_service.repository.TaskRepository;
import ai.agilery.todo_service.validation.AbstractValidationChain;
import ai.agilery.todo_service.validation.impl.TaskExistenceValidator;
import ai.agilery.todo_service.validation.impl.TaskStatusTransitionValidator;
import lombok.extern.slf4j.Slf4j;

/**
 * Validation chain for task patch operations.
 * <p>
 * This chain validates that:
 * <ul>
 *   <li>The task exists in the database</li>
 *   <li>If status is being changed, the transition is valid</li>
 * </ul>
 * </p>
 */
@Component
@Slf4j
public class TaskPatchValidationChain extends AbstractValidationChain<TaskDto> {

    /**
     * Constructs the validation chain for task patches.
     *
     * @param taskRepository the repository for accessing task data
     */
    public TaskPatchValidationChain(TaskRepository taskRepository) {
        log.info("Initializing TaskPatchValidationChain...");
        add(new TaskExistenceValidator<>(taskRepository, true));
        add(new TaskStatusTransitionValidator(taskRepository));
        log.info("TaskPatchValidationChain initialized");
    }
}
