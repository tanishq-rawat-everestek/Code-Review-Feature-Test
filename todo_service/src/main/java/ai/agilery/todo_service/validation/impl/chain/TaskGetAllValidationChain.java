package ai.agilery.todo_service.validation.impl.chain;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import ai.agilery.todo_service.validation.AbstractValidationChain;
import ai.agilery.todo_service.validation.impl.PaginationParameterValidator;
import lombok.extern.slf4j.Slf4j;

/**
 * Validation chain for retrieving all tasks with pagination.
 * <p>
 * This chain validates that:
 * <ul>
 *   <li>Pagination parameters are valid</li>
 *   <li>Sort fields are allowed</li>
 * </ul>
 * </p>
 */
@Component
@Slf4j
public class TaskGetAllValidationChain extends AbstractValidationChain<Pageable> {

    /**
     * Set of allowed fields for sorting tasks.
     */
    private static final Set<String> ALLOWED_SORT_FIELDS = Stream.of(
            "id", "title", "status", "priority", "dueDate", "completed", 
            "createdDateTime", "lastModifiedDateTime", "createdBy"
    ).collect(Collectors.toSet());

    /**
     * Constructs the validation chain for getting all tasks.
     */
    public TaskGetAllValidationChain() {
        log.info("Initializing TaskGetAllValidationChain...");
        add(new PaginationParameterValidator(ALLOWED_SORT_FIELDS, "taskDto"));
        log.info("TaskGetAllValidationChain initialized");
    }
}
