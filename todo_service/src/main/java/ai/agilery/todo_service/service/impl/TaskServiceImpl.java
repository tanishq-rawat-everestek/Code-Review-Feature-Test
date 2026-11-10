package ai.agilery.todo_service.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.agilery.todo_service.dto.TaskDto;
import ai.agilery.todo_service.entity.Task;
import ai.agilery.todo_service.entity.TaskStatus;
import ai.agilery.todo_service.exceptions.ProcessingException;
import ai.agilery.todo_service.exceptions.ResourceNotFoundException;
import ai.agilery.todo_service.mapper.TaskMapper;
import ai.agilery.todo_service.repository.TaskRepository;
import ai.agilery.todo_service.service.TaskService;
import lombok.RequiredArgsConstructor;

/**
 * Implementation of the TaskService interface.
 * Handles business logic for task management operations including CRUD operations.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    /**
     * Repository for task data access operations.
     */
    private final TaskRepository taskRepository;

    /**
     * Mapper for converting between Task entities and TaskDto objects.
     */
    private final TaskMapper taskMapper;

    /**
     * Creates a new task in the system.
     * Sets default values for status and completed flag if not provided.
     *
     * @param taskDto the task data transfer object containing task details
     * @return the created task as a DTO
     */
    @Override
    public TaskDto create(TaskDto taskDto) {
        log.info("Creating new task with title: {}", taskDto.getTitle());

        Task task = taskMapper.toEntity(taskDto);

        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.PENDING);
        }

        Task savedTask = taskRepository.save(task);

        log.info("Task created successfully with ID: {}", savedTask.getId());

        return taskMapper.toDto(savedTask);
    }

    /**
     * Updates an existing task with all provided fields.
     * Throws an exception if the task is not found.
     *
     * @param taskDto the task data transfer object containing updated task details
     * @return the updated task as a DTO
     */
    @Override
    public TaskDto update(TaskDto taskDto) {
        log.info("Updating task with ID: {}", taskDto.getId());

        Task existingTask = taskRepository.findById(taskDto.getId())
                .orElseThrow(() -> {
                    log.error("Task not found with ID: {}", taskDto.getId());
                    return new ResourceNotFoundException("Task not found with ID: " + taskDto.getId());
                });

        taskMapper.update(existingTask, taskDto);

        if (existingTask.getStatus() == TaskStatus.COMPLETED) {
            existingTask.setCompleted(true);
        } else {
            existingTask.setCompleted(false);
        }

        Task updatedTask = taskRepository.save(existingTask);
        log.info("Task with ID: {} updated successfully", updatedTask.getId());

        return taskMapper.toDto(updatedTask);
    }

    /**
     * Partially updates an existing task with only the provided fields.
     * Null fields in the DTO are ignored and not updated.
     * Throws an exception if the task is not found.
     *
     * @param taskDto the task data transfer object containing fields to update
     * @return the patched task as a DTO
     */
    @Override
    public TaskDto patch(TaskDto taskDto) {
        log.info("Patching task with ID: {}", taskDto.getId());

        Task existingTask = taskRepository.findById(taskDto.getId())
                .orElseThrow(() -> {
                    log.error("Task not found with ID: {}", taskDto.getId());
                    return new ResourceNotFoundException("Task not found with ID: " + taskDto.getId());
                });

        taskMapper.patch(existingTask, taskDto);

        if (existingTask.getStatus() == TaskStatus.COMPLETED) {
            existingTask.setCompleted(true);
        } else if (taskDto.getStatus() != null) {
            existingTask.setCompleted(false);
        }

        Task patchedTask = taskRepository.save(existingTask);
        log.info("Task with ID: {} patched successfully", patchedTask.getId());

        return taskMapper.toDto(patchedTask);
    }

    /**
     * Retrieves a task by its unique identifier.
     * Throws an exception if the task is not found.
     *
     * @param id the unique identifier of the task
     * @return the task as a DTO
     */
    @Override
    @Transactional(readOnly = true)
    public TaskDto get(UUID id) {
        log.info("Retrieving task with ID: {}", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Task not found with ID: {}", id);
                    return new ResourceNotFoundException("Task not found with ID: " + id);
                });

        return taskMapper.toDto(task);
    }

    /**
     * Retrieves all tasks with pagination support.
     *
     * @param pageable the pagination information including page number, size, and sorting
     * @return a page of tasks as DTOs
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TaskDto> getAll(Pageable pageable) {
        log.info("Retrieving all tasks with pagination: {}", pageable);

        Page<Task> tasksPage = taskRepository.findAll(pageable);

        return tasksPage.map(taskMapper::toDto);
    }

    /**
     * Deletes a task by its unique identifier.
     * Performs a hard delete, removing the task from the database.
     * Throws an exception if the task is not found.
     *
     * @param id the unique identifier of the task to delete
     * @throws ResourceNotFoundException if the task is not found
     */
    @Override
    public void delete(UUID id) {
        log.info("Attempting to delete task with ID: {}", id);

        if (!taskRepository.existsById(id)) {
            log.error("Task not found with ID: {}", id);
            throw new ResourceNotFoundException("Task not found with ID: " + id);
        }

        taskRepository.deleteById(id);
        log.info("Task with ID: {} deleted successfully", id);
    }
}
