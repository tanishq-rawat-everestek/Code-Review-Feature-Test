package ai.agilery.todo_service.validation.impl.chain;

import java.util.UUID;

import org.springframework.stereotype.Component;

import ai.agilery.todo_service.repository.TaskRepository;
import ai.agilery.todo_service.validation.AbstractValidationChain;
import ai.agilery.todo_service.validation.impl.TaskExistenceValidator;
import lombok.extern.slf4j.Slf4j;

/**
 * Validation chain for retrieving a single task.
 * <p>
 * This chain validates that the task exists in the database.
 * </p>
 */
@Component
@Slf4j
public class TaskGetValidationChain extends AbstractValidationChain<UUID> {

    /**
     * Constructs the validation chain for getting a task.
     *
     * @param taskRepository the repository for accessing task data
     */
    public TaskGetValidationChain(TaskRepository taskRepository) {
        log.info("Initializing TaskGetValidationChain...");
        add(new TaskExistenceValidator<>(taskRepository, true));
        log.info("TaskGetValidationChain initialized");
    }
}
