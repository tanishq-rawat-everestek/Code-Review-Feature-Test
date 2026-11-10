package ai.agilery.todo_service.validation.impl.chain;

import org.springframework.stereotype.Component;

import ai.agilery.todo_service.dto.TaskDto;
import ai.agilery.todo_service.repository.TaskRepository;
import ai.agilery.todo_service.validation.AbstractValidationChain;
import ai.agilery.todo_service.validation.impl.TaskExistenceValidator;
import lombok.extern.slf4j.Slf4j;

/**
 * Validation chain for task update operations.
 * <p>
 * This chain validates that:
 * <ul>
 *   <li>The task exists in the database</li>
 * </ul>
 * </p>
 */
@Component
@Slf4j
public class TaskUpdateValidationChain extends AbstractValidationChain<TaskDto> {

    /**
     * Constructs the validation chain for task updates.
     *
     * @param taskRepository the repository for accessing task data
     */
    public TaskUpdateValidationChain(TaskRepository taskRepository) {
        log.info("Initializing TaskUpdateValidationChain...");
        add(new TaskExistenceValidator<>(taskRepository, true));
        log.info("TaskUpdateValidationChain initialized");
    }
}
