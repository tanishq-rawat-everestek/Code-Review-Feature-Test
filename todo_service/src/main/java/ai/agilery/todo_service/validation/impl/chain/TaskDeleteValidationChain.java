package ai.agilery.todo_service.validation.impl.chain;

import java.util.UUID;

import org.springframework.stereotype.Component;

import ai.agilery.todo_service.repository.TaskRepository;
import ai.agilery.todo_service.validation.AbstractValidationChain;
import ai.agilery.todo_service.validation.impl.TaskDeletionStatusValidator;
import ai.agilery.todo_service.validation.impl.TaskExistenceValidator;
import lombok.extern.slf4j.Slf4j;

/**
 * Validation chain for task deletion operations.
 * <p>
 * This chain validates that:
 * <ul>
 *   <li>The task exists in the database</li>
 *   <li>The task's status allows deletion (PENDING or CANCELLED only)</li>
 * </ul>
 * </p>
 */
@Component
@Slf4j
public class TaskDeleteValidationChain extends AbstractValidationChain<UUID> {

    /**
     * Constructs the validation chain for task deletion.
     *
     * @param taskRepository the repository for accessing task data
     */
    public TaskDeleteValidationChain(TaskRepository taskRepository) {
        log.info("Initializing TaskDeleteValidationChain...");
        add(new TaskExistenceValidator<>(taskRepository, true));
        add(new TaskDeletionStatusValidator(taskRepository));
        log.info("TaskDeleteValidationChain initialized");
    }
}
