package ai.agilery.todo_service.validation.impl.chain;

import org.springframework.stereotype.Component;

import ai.agilery.todo_service.dto.TaskDto;
import ai.agilery.todo_service.validation.AbstractValidationChain;
import lombok.extern.slf4j.Slf4j;

/**
 * Validation chain for task creation operations.
 * <p>
 * This chain validates that all required fields are present and valid
 * for creating a new task. No additional semantic validations are required
 * for task creation as tasks are independent entities.
 * </p>
 */
@Component
@Slf4j
public class TaskCreateValidationChain extends AbstractValidationChain<TaskDto> {

    /**
     * Constructs the validation chain for task creation.
     * <p>
     * Currently, no additional validators are needed as syntactic validation
     * is handled by Jakarta validation annotations on the DTO.
     * </p>
     */
    public TaskCreateValidationChain() {
        log.info("Initializing TaskCreateValidationChain...");
        log.info("TaskCreateValidationChain initialized");
    }
}
