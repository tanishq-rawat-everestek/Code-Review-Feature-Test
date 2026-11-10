package ai.agilery.todo_service.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.agilery.todo_service.dto.TaskDto;

/**
 * Service interface for managing Task entities.
 * Provides business operations for creating, reading, updating, and deleting tasks.
 */
public interface TaskService {

    /**
     * Creates a new task.
     *
     * @param taskDto the task data transfer object containing task details
     * @return the created task as a DTO
     */
    TaskDto create(TaskDto taskDto);

    /**
     * Updates an existing task with all fields.
     *
     * @param taskDto the task data transfer object containing updated task details
     * @return the updated task as a DTO
     */
    TaskDto update(TaskDto taskDto);

    /**
     * Partially updates an existing task with only provided fields.
     *
     * @param taskDto the task data transfer object containing fields to update
     * @return the patched task as a DTO
     */
    TaskDto patch(TaskDto taskDto);

    /**
     * Retrieves a task by its unique identifier.
     *
     * @param id the unique identifier of the task
     * @return the task as a DTO
     */
    TaskDto get(UUID id);

    /**
     * Retrieves all tasks with pagination support.
     *
     * @param pageable the pagination information
     * @return a page of tasks as DTOs
     */
    Page<TaskDto> getAll(Pageable pageable);

    /**
     * Deletes a task by its unique identifier.
     *
     * @param id the unique identifier of the task to delete
     */
    void delete(UUID id);
}
